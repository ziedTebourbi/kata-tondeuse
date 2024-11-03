package com.example.batchprocessing.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.example.batchprocessing.domain.model.LawnMower;
import com.example.batchprocessing.domain.model.Position;
import com.example.batchprocessing.service.CommandProcessor;

public class LawnMowerProcessor implements ItemProcessor<LawnMower, Position> {

    private static final Logger logger = LoggerFactory.getLogger(LawnMowerProcessor.class);

    private CommandProcessor commandProcessor;

    public LawnMowerProcessor(CommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
    }

    @Override
    public Position process(LawnMower lawnMower) {
        logger.info("Processing LawnMower with initial position: {}", lawnMower.getPosition());
        logger.info("Instructions: {}", lawnMower.getInstructions());

        // Exécuter les instructions de déplacement
        for (char command : lawnMower.getInstructions().toCharArray()) {
            commandProcessor.processCommand(command, lawnMower);
            logger.info("Executed command '{}', new position: {}", command, lawnMower.getPosition());
        }

        logger.info("LawnMower final position after processing: {}", lawnMower.getPosition());

        // Retourner la position finale de la tondeuse
        return lawnMower.getPosition();
    }
}
