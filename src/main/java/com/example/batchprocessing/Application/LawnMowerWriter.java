package com.example.batchprocessing.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import com.example.batchprocessing.domain.model.Position;

public class LawnMowerWriter implements ItemWriter<Position> {

	private static final Logger logger = LoggerFactory.getLogger(LawnMowerWriter.class);

	@Override
	public void write(Chunk<? extends Position> positions) throws Exception {
		positions.getItems().forEach(position -> logger.info("Position: {}", position.toString()));
	}
}
