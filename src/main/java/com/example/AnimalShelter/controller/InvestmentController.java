package com.example.AnimalShelter.controller;

import com.example.AnimalShelter.exception.ResourceNotFoundException;
import com.example.AnimalShelter.model.*;
import com.example.AnimalShelter.repository.FounderRepository;
import com.example.AnimalShelter.repository.InvestmentRepository;
import com.example.AnimalShelter.repository.ShelterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class InvestmentController {
    @Autowired
    private InvestmentRepository investmentRepository;

    @Autowired
    private ShelterRepository shelterRepository;

    @Autowired
    private FounderRepository founderRepository;

    @PostMapping("/investments/{shelter_id}/{founder_id}")
    public ResponseEntity<Investment> addInvestment(@PathVariable(value = "shelter_id") Long shelterId,@PathVariable(value = "founder_id") Long founderId, @RequestBody Investment investmentRequest){
        Investment investment = shelterRepository.findById(shelterId).map(sh -> {
            investmentRequest.setfId(founderId);
            investmentRequest.setFounder(founderRepository.findFounderById(founderId));
            investmentRequest.setShelter(sh);
            investmentRequest.setsId(shelterId);
            return investmentRepository.save(investmentRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Shelter with id = " + shelterId));

        return new ResponseEntity<>(investment, HttpStatus.CREATED);
    }

    @GetMapping("/investments")
    public ResponseEntity<List<Investment>> getAllInvestments(@RequestParam(required = false) String name){
        List<Investment> investments = new ArrayList<Investment>();
        investmentRepository.findAll().forEach(investments::add);
        if (investments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(investments, HttpStatus.OK);
    }

    @PutMapping("/investments/{id}")
    public ResponseEntity<Investment> updateInvestment(@PathVariable("id") long id, @RequestBody Investment investmentRequest){
        Investment investment = investmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Animal " + id + "not found"));

        investment.setInvestedMoney(investmentRequest.getInvestedMoney());
        investment.setOwnedPercentage(investmentRequest.getOwnedPercentage());

        return new ResponseEntity<>(investmentRepository.save(investment), HttpStatus.OK);
    }

    @DeleteMapping("/investments/{id}")
    public ResponseEntity<HttpStatus> deleteInvestment(@PathVariable("id") long id){
        Investment investment = investmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Animal " + id + "not found"));
        investmentRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/investments/{id}")
    public ResponseEntity<InvestmentDTO> getAnimalById(@PathVariable("id") long id){
        Investment investment = investmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Animal with id = " + id));
        InvestmentDTO investmentDTO = null;
        Shelter shelter = investment.getShelter();
        ShelterDTO shelterDTO = new ShelterDTO(shelter.getId(),shelter.getLocation(),shelter.getName(),shelter.getUsable_area_in_sq(),shelter.getDescription(),shelter.getDateOfConstruction());
        Founder founder = investment.getFounder();
        FounderDTO founderDTO = new FounderDTO(founder.getId(),founder.getName(),founder.getOccupation(),founder.getMarried(),founder.getDescription(),founder.getDateOfBirth());

        investmentDTO = new InvestmentDTO(investment.getId(),investment.getInvestedMoney(),investment.getOwnedPercentage(),shelterDTO,founderDTO);

        return new ResponseEntity<>(investmentDTO, HttpStatus.OK);
    }
}
