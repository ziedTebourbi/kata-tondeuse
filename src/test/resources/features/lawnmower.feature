Feature: Automatic lawnmower navigation
  As a user, I want the lawnmower to navigate a lawn according to instructions and stay within boundaries

  Background:
    Given a lawn with upper-right corner coordinates of 5 and 5

  Scenario: Move the first lawnmower to the correct final position
    Given a lawnmower at position 1, 2 facing N
    When the lawnmower receives instructions "GAGAGAGAA"
    Then the final position should be 1, 3 facing N

  Scenario: Move the second lawnmower to the correct final position
    Given a lawnmower at position 3, 3 facing E
    When the lawnmower receives instructions "AADAADADDA"
    Then the final position should be 5, 1 facing E

  Scenario: Lawn boundary check at the bottom
    Given a lawnmower at position 0, 0 facing S
    When the lawnmower receives instructions "A"
    Then the final position should be 0, 0 facing S

  Scenario: Lawn boundary check at the top
    Given a lawnmower at position 5, 5 facing N
    When the lawnmower receives instructions "A"
    Then the final position should be 5, 5 facing N

  Scenario: Invalid orientation instruction
    Given a lawnmower at position 2, 2 facing W
    When the lawnmower receives an invalid instruction "X"
    Then an error should occur
