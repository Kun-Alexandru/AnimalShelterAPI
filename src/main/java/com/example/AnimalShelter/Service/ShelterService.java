package com.example.AnimalShelter.Service;

import com.example.AnimalShelter.model.ShelterNoAnimalsDTO;
import com.example.AnimalShelter.repository.ShelterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.AnimalShelter.model.ShelterDTO;

import java.util.List;

public interface ShelterService {
    List<ShelterDTO> getPaginatedShelters(int currentPage, int itemsPerPage, String filterValue);
    List<ShelterNoAnimalsDTO> orderShelterByNumberAnimals();
}