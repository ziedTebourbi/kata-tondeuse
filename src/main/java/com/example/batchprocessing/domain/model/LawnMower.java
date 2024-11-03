package com.example.batchprocessing.domain.model;

import com.example.batchprocessing.domain.exception.InvalidDataFormatException;

public class LawnMower {
    private Position position;
    private final int maxX;
    private final int maxY;
    private String instructions;

    public LawnMower(Position position, int maxX, int maxY, String instructions)throws InvalidDataFormatException {
        this.position = position;
        this.maxX = maxX;
        this.maxY = maxY;
        validateInstructions(instructions);
        this.instructions = instructions;
    }
    
    private void validateInstructions(String instructions) throws InvalidDataFormatException {

        if (!instructions.matches("[DGA]+")) {

            throw new InvalidDataFormatException("Instructions contain invalid characters.");

        }

    }

    public Position getPosition() {
        return position;
    }

    public String getInstructions() {
        return instructions;
    }

    public void turnLeft() {
        position.setOrientation(position.getOrientation().turnLeft());
    }

    public void turnRight() {
        position.setOrientation(position.getOrientation().turnRight());
    }

    public void moveForward() {
        // Créer une position temporaire pour vérifier les limites
        Position tempPosition = new Position(position.getX(), position.getY(), position.getOrientation());
        
        // Avancer selon l'orientation
        tempPosition.getOrientation().advance(tempPosition);

        // Vérifier les limites avant de mettre à jour la position réelle
        if (tempPosition.getX() >= 0 && tempPosition.getX() <= maxX &&
            tempPosition.getY() >= 0 && tempPosition.getY() <= maxY) {
            position = tempPosition;
        }
    }

	@Override
	public String toString() {
		return "LawnMower [position=" + position + ", maxX=" + maxX + ", maxY=" + maxY + ", instructions="
				+ instructions + "]";
	}
}
