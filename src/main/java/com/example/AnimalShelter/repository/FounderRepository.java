package com.example.AnimalShelter.repository;

import com.example.AnimalShelter.model.Founder;
import com.example.AnimalShelter.model.FounderAvgDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FounderRepository extends JpaRepository<Founder, Long>, FounderRepositoryCustom {
    List<Founder> findByNameContaining(String name);
    Founder findFounderById (long id);

    @Query("SELECT NEW com.example.AnimalShelter.model.FounderAvgDTO(f.id, f.name, AVG(i.investedMoney)) " +
            "FROM Founder f " +
            "JOIN f.investments i " +
            "GROUP BY f.id, f.name " +
            "ORDER BY AVG(i.investedMoney) DESC")
    List<FounderAvgDTO> OrderFoundersByAvgInvestment();
    //jpql
    //order founders by the average invested amount
}