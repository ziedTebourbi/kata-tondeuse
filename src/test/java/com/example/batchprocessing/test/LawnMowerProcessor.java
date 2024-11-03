package com.example.batchprocessing.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.example.batchprocessing.Application.LawnMowerProcessor;
import com.example.batchprocessing.domain.model.LawnMower;
import com.example.batchprocessing.domain.model.Position;
import com.example.batchprocessing.domain.model.Orientation;
import com.example.batchprocessing.service.CommandProcessor;

class LawnMowerProcessorTest {

    private final CommandProcessor commandProcessor = new CommandProcessor();
    private final LawnMowerProcessor processor = new LawnMowerProcessor(commandProcessor);

    @Test
    void testProcessLawnMower() {
        LawnMower mower = new LawnMower(new Position(1, 2, Orientation.N), 5, 5, "GAGAGAGAA");
        Position finalPosition = processor.process(mower);
        assertEquals(1, finalPosition.getX());
        assertEquals(3, finalPosition.getY());
        assertEquals(Orientation.N, finalPosition.getOrientation());
    }

    @Test
    void testProcessLawnMowerWithBoundary() {
        LawnMower mower = new LawnMower(new Position(0, 0, Orientation.S), 5, 5, "AAAA");
        Position finalPosition = processor.process(mower);
        assertEquals(0, finalPosition.getX());
        assertEquals(0, finalPosition.getY());
        assertEquals(Orientation.S, finalPosition.getOrientation());
    }
}

