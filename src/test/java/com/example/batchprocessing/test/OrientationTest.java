package com.example.batchprocessing.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.example.batchprocessing.domain.model.Orientation;
import com.example.batchprocessing.domain.model.Position;

class OrientationTest {

    @Test
    void testTurnLeft() {
        assertEquals(Orientation.W, Orientation.N.turnLeft());
        assertEquals(Orientation.N, Orientation.E.turnLeft());
        assertEquals(Orientation.E, Orientation.S.turnLeft());
        assertEquals(Orientation.S, Orientation.W.turnLeft());
    }

    @Test
    void testTurnRight() {
        assertEquals(Orientation.E, Orientation.N.turnRight());
        assertEquals(Orientation.S, Orientation.E.turnRight());
        assertEquals(Orientation.W, Orientation.S.turnRight());
        assertEquals(Orientation.N, Orientation.W.turnRight());
    }

    @Test
    void testAdvance() {
        Position pos = new Position(2, 2, Orientation.N);
        Orientation.N.advance(pos);
        assertEquals(3, pos.getY());

        pos.setOrientation(Orientation.E);
        Orientation.E.advance(pos);
        assertEquals(3, pos.getX());
    }
}
