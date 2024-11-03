package com.example.batchprocessing.steps;

import com.example.batchprocessing.domain.model.LawnMower;
import com.example.batchprocessing.domain.model.Position;
import com.example.batchprocessing.domain.model.Orientation;
import com.example.batchprocessing.service.CommandProcessor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.*;

public class LawnMowerStepDefinitions {

    private LawnMower lawnMower;
    private Position initialPosition;
    private CommandProcessor commandProcessor = new CommandProcessor();
    private Exception exception;

    @Given("a lawn with upper-right corner coordinates of {int} and {int}")
    public void a_lawn_with_coordinates(int maxX, int maxY) {
        // Set the boundaries, used only for information here
    }

    @Given("a lawnmower at position {int}, {int} facing {word}")
    public void a_lawnmower_at_position(int x, int y, String orientation) {
        this.initialPosition = new Position(x, y, Orientation.valueOf(orientation));
        this.lawnMower = new LawnMower(initialPosition, 5, 5, "");
    }

    @When("the lawnmower receives instructions {string}")
    public void the_lawnmower_receives_instructions(String instructions) {
        this.lawnMower = new LawnMower(lawnMower.getPosition(), 5, 5, instructions);
        for (char command : instructions.toCharArray()) {
            commandProcessor.processCommand(command, lawnMower);
        }
    }

    @When("the lawnmower receives an invalid instruction {string}")
    public void the_lawnmower_receives_invalid_instruction(String invalidInstruction) {
        try {
            commandProcessor.processCommand(invalidInstruction.charAt(0), lawnMower);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the final position should be {int}, {int} facing {word}")
    public void the_final_position_should_be(int expectedX, int expectedY, String expectedOrientation) {
        assertEquals(expectedX, lawnMower.getPosition().getX());
        assertEquals(expectedY, lawnMower.getPosition().getY());
        assertEquals(Orientation.valueOf(expectedOrientation), lawnMower.getPosition().getOrientation());
    }

    @Then("an error should occur")
    public void an_error_should_occur() {
        assertNotNull(exception, "Expected an exception to be thrown");
    }
}
