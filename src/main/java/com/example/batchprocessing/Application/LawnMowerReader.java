package com.example.batchprocessing.Application;

import java.util.Iterator;
import java.util.List;

import org.springframework.batch.item.ItemReader;

import com.example.batchprocessing.domain.model.LawnMower;
import com.example.batchprocessing.port.LawnMowerRepository;

public class LawnMowerReader implements ItemReader<LawnMower> {
	
	

    private final Iterator<LawnMower> lawnMowerIterator;

    public LawnMowerReader(LawnMowerRepository repository) {
        // Charger la liste des tondeuses une seule fois
        List<LawnMower> lawnMowers = repository.loadLawnMowers();
        this.lawnMowerIterator = lawnMowers.iterator();
    }

    @Override
    public LawnMower read() {
        // Avancer dans l'itérateur et retourner null si toutes les tondeuses ont été lues
        return lawnMowerIterator.hasNext() ? lawnMowerIterator.next() : null;
    }
}
