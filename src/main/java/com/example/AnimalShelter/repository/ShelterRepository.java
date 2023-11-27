package com.example.AnimalShelter.repository;

import com.example.AnimalShelter.model.Shelter;
import com.example.AnimalShelter.model.ShelterNoAnimalsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShelterRepository extends JpaRepository<Shelter, Long>, ShelterRepositoryCustom {
    List<Shelter> findByNameContaining(String name);
    Shelter findShelterById (long id);

    @Query("SELECT NEW com.example.AnimalShelter.model.ShelterNoAnimalsDTO(s.id, s.name,s.location, COUNT(a)) " +
            "FROM Shelter s " +
            "LEFT JOIN s.animals a " +
            "GROUP BY s.id, s.name " +
            "ORDER BY COUNT(a) ASC")
    List<ShelterNoAnimalsDTO> OrderShelterAscNoAnimals();

}