package com.example.AnimalShelter.repository;

import com.example.AnimalShelter.model.Investment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvestmentRepository extends JpaRepository<Investment,Long>{
    List<Investment> findByShelterId(Long id);
    List<Investment> findByFounderId(Long id);
}