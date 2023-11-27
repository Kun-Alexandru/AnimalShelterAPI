package com.example.AnimalShelter.repository;

import com.example.AnimalShelter.model.Animal;
import com.example.AnimalShelter.model.Shelter;
import com.example.AnimalShelter.model.ShelterNoAnimalsDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.util.List;

public class ShelterRepositoryCustomImpl implements ShelterRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ShelterNoAnimalsDTO> find1() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ShelterNoAnimalsDTO> criteriaQuery = criteriaBuilder.createQuery(ShelterNoAnimalsDTO.class);
        Root<Shelter> shelter = criteriaQuery.from(Shelter.class);

        Join<Shelter, Animal> shelterAnimalJoin = shelter.join("animals", JoinType.LEFT);
        Path<Long> amount = shelterAnimalJoin.get("isNeutered");
        Path<Long> shelterId = shelter.get("id");
        Path<String> shelterName = shelter.get("name");
        Path<String> shelterLocation = shelter.get("location");
        criteriaQuery.multiselect(
                shelterId,
                shelterName,
                shelterLocation,
                criteriaBuilder.count(amount).alias("isNeutered")
        ).groupBy(shelterId);

        criteriaQuery.orderBy(criteriaBuilder.asc(criteriaBuilder.count(amount)));

        TypedQuery<ShelterNoAnimalsDTO> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }
}