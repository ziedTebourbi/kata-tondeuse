package com.example.batchprocessing.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.example.batchprocessing.domain.model.LawnMower;
import com.example.batchprocessing.domain.model.Orientation;
import com.example.batchprocessing.domain.model.Position;

class LawnMowerTest {

    @Test
    void testTurnLeft() {
        LawnMower mower = new LawnMower(new Position(1, 2, Orientation.N), 5, 5, "G");
        mower.turnLeft();
        assertEquals(Orientation.W, mower.getPosition().getOrientation());
    }

    @Test
    void testTurnRight() {
        LawnMower mower = new LawnMower(new Position(1, 2, Orientation.N), 5, 5, "D");
        mower.turnRight();
        assertEquals(Orientation.E, mower.getPosition().getOrientation());
    }

    @Test
    void testMoveForwardWithinBounds() {
        LawnMower mower = new LawnMower(new Position(1, 2, Orientation.N), 5, 5, "A");
        mower.moveForward();
        assertEquals(3, mower.getPosition().getY());
        assertEquals(1, mower.getPosition().getX());
    }

    @Test
    void testMoveForwardOutOfBounds() {
        LawnMower mower = new LawnMower(new Position(0, 5, Orientation.N), 5, 5, "A");
        mower.moveForward();
        assertEquals(5, mower.getPosition().getY());  // No movement as it's out of bounds
        assertEquals(0, mower.getPosition().getX());
    }
}
