package com.example.batchprocessing.test;

import com.example.batchprocessing.Application.LawnMowerWriter;
import com.example.batchprocessing.domain.model.Orientation;
import com.example.batchprocessing.domain.model.Position;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBatchTest
@SpringBootTest
@ActiveProfiles("test")
class LawnMowerWriterIntegrationTest {

    @Autowired
    private LawnMowerWriter writer;

    @Test
    void testLawnMowerWriter() throws Exception {
        Position position = new Position(1, 3, Orientation.N);
        Chunk<Position> chunk = new Chunk<>(List.of(position));
        
        assertDoesNotThrow(() -> writer.write(chunk), "Writing the chunk should not throw an exception");
    }
}
