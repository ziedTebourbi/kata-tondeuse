package com.example.batchprocessing.service;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import com.example.batchprocessing.domain.model.LawnMower;



@Service
public class CommandProcessor {
    private final Map<Character, Consumer<LawnMower>> commands = new HashMap<>();

    public CommandProcessor() {
        commands.put('A', LawnMower::moveForward);
        commands.put('G', LawnMower::turnLeft);
        commands.put('D', LawnMower::turnRight);
    }

    public void processCommand(Character command, LawnMower lawnMower) {
        Consumer<LawnMower> action = commands.get(command);
        if (action != null) {
            action.accept(lawnMower);
        }
    }
}
