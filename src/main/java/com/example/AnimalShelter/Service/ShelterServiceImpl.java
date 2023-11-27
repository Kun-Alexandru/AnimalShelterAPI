package com.example.AnimalShelter.Service;

import com.example.AnimalShelter.model.Shelter;
import com.example.AnimalShelter.model.ShelterDTO;
import com.example.AnimalShelter.model.ShelterNoAnimalsDTO;
import com.example.AnimalShelter.repository.ShelterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShelterServiceImpl implements ShelterService {
    private final ShelterRepository shelterRepository;

    @Autowired
    public ShelterServiceImpl(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    @Override
    public List<ShelterDTO> getPaginatedShelters(int currentPage, int itemsPerPage, String filterValue) {
        List<Shelter> shelters;
        if (filterValue == null || filterValue.trim().isEmpty()) {
            shelters = shelterRepository.findAll();
        } else {
            shelters = shelterRepository.findByNameContaining(filterValue);
        }

        int totalItems = shelters.size();
        int startIndex = (currentPage - 1) * itemsPerPage;
        int endIndex = Math.min(currentPage * itemsPerPage, totalItems);

        shelters = shelters.subList(startIndex, endIndex);

        List<ShelterDTO> shelterDTOs = new ArrayList<>();
        for (Shelter shelter : shelters) {
            ShelterDTO shelterDTO = new ShelterDTO(
                    shelter.getId(),
                    shelter.getLocation(),
                    shelter.getName(),
                    shelter.getUsable_area_in_sq(),
                    shelter.getDescription(),
                    shelter.getDateOfConstruction()
            );
            shelterDTOs.add(shelterDTO);
        }

        return shelterDTOs;
    }

    @Override
    public List<ShelterNoAnimalsDTO> orderShelterByNumberAnimals() {
        return shelterRepository.find1();

    }
}