package com.example.AnimalShelter.repository;

import com.example.AnimalShelter.model.FounderAvgDTO;
import com.example.AnimalShelter.model.FounderIntDTO;

import java.util.List;

public interface FounderRepositoryCustom {
    List<FounderAvgDTO> find2();

    List<FounderIntDTO> find3();
}
