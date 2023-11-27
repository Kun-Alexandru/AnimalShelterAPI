package com.example.AnimalShelter.repository;
import com.example.AnimalShelter.model.Animal;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal,Long> {

    List<Animal> findByShelterId(Long shelterId);

    @Transactional
    void deleteByShelterId(long shelterId);
}
