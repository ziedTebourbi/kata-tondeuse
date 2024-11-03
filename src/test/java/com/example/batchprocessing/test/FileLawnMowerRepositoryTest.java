package com.example.batchprocessing.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.batchprocessing.infrastructure.adapter.output.FileLawnMowerRepository;
import com.example.batchprocessing.domain.model.LawnMower;
import java.util.List;

class FileLawnMowerRepositoryTest {

    @Autowired
    private FileLawnMowerRepository repository = new FileLawnMowerRepository("mower_instructions.txt");

    @Test
    void testLoadLawnMowers() {
        List<LawnMower> mowers = repository.loadLawnMowers();
        assertNotNull(mowers);
        assertFalse(mowers.isEmpty(), "Lawn mowers should be loaded from file");
        assertEquals(2, mowers.size(), "Expected 2 mowers as per test file data");
    }
}

