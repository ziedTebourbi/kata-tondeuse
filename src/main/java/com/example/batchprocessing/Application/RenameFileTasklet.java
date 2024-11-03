package com.example.batchprocessing.Application;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class RenameFileTasklet implements Tasklet {

    @Value("${mower.input-file}")
    private String filePath;  // Chemin du fichier dans les ressources

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws IOException, URISyntaxException {
     
        Path sourcePath = Paths.get(getClass().getClassLoader().getResource(filePath).toURI());
        
      
        Path targetDirectory = Paths.get("target/renamed_files/");
        Files.createDirectories(targetDirectory);  // Créer le répertoire si non existant

     
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String newFileName = "mower_instructions_" + timestamp + ".txt";
        Path targetPath = targetDirectory.resolve(newFileName);

    
        Files.copy(sourcePath, targetPath);
        System.out.println("File copied and renamed to: " + targetPath.toAbsolutePath());

        return RepeatStatus.FINISHED;
    }
}
