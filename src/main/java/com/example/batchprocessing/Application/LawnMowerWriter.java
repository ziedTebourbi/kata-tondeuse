package com.example.batchprocessing.Application;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.example.batchprocessing.domain.model.Position;





public class LawnMowerWriter implements ItemWriter<Position> {

   

  

    @Override
    public void write(Chunk<? extends Position> positions) throws Exception {
        positions.getItems().stream().forEach(Position::toString);
    }
}
