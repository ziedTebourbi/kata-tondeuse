package com.example.batchprocessing.infrastructure.adapter.output;

import com.example.batchprocessing.domain.exception.InvalidDataFormatException;
import com.example.batchprocessing.domain.exception.OrientationNotFoundException;
import com.example.batchprocessing.domain.model.LawnMower;
import com.example.batchprocessing.domain.model.Orientation;
import com.example.batchprocessing.domain.model.Position;
import com.example.batchprocessing.infrastructure.exception.FileNotFoundException;
import com.example.batchprocessing.port.LawnMowerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileLawnMowerRepository implements LawnMowerRepository {
	private static final Logger logger = LoggerFactory.getLogger(FileLawnMowerRepository.class);
    private final String fileName;


    @Value("{error.message.file.not.found.in.classpath}")
    String errorMessageNotFoundInClasspath;
    @Value("{error.message.the.file.is.empty.or.does.not.contain.valid.data}")
    String errorMessageFileIsEmptyOrDoesNotContainValidData;
    @Value("{error.message.Invalid.data.format.in.the.input.file}")
    String errorMessageInvalidDataFormatInTheInputFile;
    @Value("{error.message.Invalid.lawn.limits.format}")
    String errorMessageInvalidLawnLimitsFormat;
    @Value("{error.message.Invalid.mower.position.format}")
    String errorMessageInvalidMowerPositionFormat;
    @Value("{error.message.Invalid.orientation.value}")
    String errorMessageInvalidOrientationValue;
    @Value("{error.message.Error.reading.file}")
    String errorMessageErrorReadingFile;


    public FileLawnMowerRepository(@Value("${mower.input-file}") String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<LawnMower> loadLawnMowers() {
        List<LawnMower> lawnMowers = new ArrayList<>();

       logger.info("Attempting to load file: {}" , fileName);

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            if (inputStream == null) {
            	  throw new FileNotFoundException(errorMessageNotFoundInClasspath + fileName);
            }

            logger.info("File loaded successfully!");

            // Lire la seule ligne du fichier
            String line = reader.readLine();
            if (line == null || line.isEmpty()) {
            	 logger.error(errorMessageFileIsEmptyOrDoesNotContainValidData);
                return lawnMowers;
            }

            // Diviser la ligne en parties
            String[] parts = line.split(" ");
            if (parts.length < 5) {
            	 logger.error("The data format is incorrect or incomplete.");
            	  throw new InvalidDataFormatException(errorMessageInvalidDataFormatInTheInputFile);
            }

            // Récupérer les limites de la pelouse (premiers éléments)
            int maxX;
			int maxY;
			try {
				maxX = Integer.parseInt(parts[0]);
				maxY = Integer.parseInt(parts[1]);
				logger.info("Lawn limits: {} x  {} y" , maxX , maxY);
			} catch (NumberFormatException e) {
				   throw new InvalidDataFormatException(errorMessageInvalidLawnLimitsFormat);
			}

            // Parcourir les tondeuses
            int i = 2;  // Commencer après les limites
            while (i < parts.length) {
                // Récupérer la position initiale et l'orientation de la tondeuse
                int x;
				int y;
				try {
					x = Integer.parseInt(parts[i]);
					y = Integer.parseInt(parts[i + 1]);
				} catch (Exception e) {
					 throw new InvalidDataFormatException(errorMessageInvalidMowerPositionFormat);
				}
                Orientation orientation;
				try {
					orientation = Orientation.valueOf(parts[i + 2]);
				} catch (Exception e) {
				    throw new OrientationNotFoundException(errorMessageInvalidOrientationValue);
				}
                Position position = new Position(x, y, orientation);

                // Récupérer les instructions
                String instructions = parts[i + 3];
                logger.info("Loaded LawnMower - Position: {} , Instructions: {}" , position , instructions);

                // Créer la tondeuse avec les limites et les instructions
                LawnMower lawnMower = new LawnMower(position, maxX, maxY, instructions);
                lawnMowers.add(lawnMower);

                // Passer à la prochaine tondeuse (avancer de 4 pour position + 1 pour les instructions)
                i += 4;
            }

        } catch (Exception e) {
        	   throw new RuntimeException(errorMessageErrorReadingFile+ e.getMessage(), e);
        }

        logger.info("Total LawnMowers loaded: {} " , lawnMowers.size());
        return lawnMowers;
    }
}
