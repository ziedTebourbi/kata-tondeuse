package com.example.batchprocessing.port;


import java.util.List;

import com.example.batchprocessing.domain.model.LawnMower;



public interface LawnMowerRepository {
    List<LawnMower> loadLawnMowers();
 
}
