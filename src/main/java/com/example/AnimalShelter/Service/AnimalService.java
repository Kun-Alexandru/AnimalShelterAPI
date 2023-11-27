package com.example.AnimalShelter.Service;

import com.example.AnimalShelter.model.Animal;
import com.example.AnimalShelter.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository repo;

    public AnimalService(AnimalRepository repo) {
        this.repo = repo;
    }


    public List<Animal> getAll(){
        return repo.findAll();
    }
    public List<Animal> filterAnimal(int numberLegs){
        return repo.findAll().stream().filter(r -> r.getNumber_of_legs() > numberLegs).collect(Collectors.toList());
    }
}
