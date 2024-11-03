package com.example.batchprocessing.test;


import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.example.batchprocessing.domain.exception.InvalidDataFormatException;
import com.example.batchprocessing.domain.model.LawnMower;
import com.example.batchprocessing.domain.model.Orientation;
import com.example.batchprocessing.domain.model.Position;
import com.example.batchprocessing.service.CommandProcessor;

class CommandProcessorTest {

    @Test
    void testProcessUnknownCommandThrowsException() {
        Position position = new Position(0, 0, Orientation.N);
        assertThrows(InvalidDataFormatException.class, () ->  new LawnMower(position, 5, 5, ""));

    }
}
