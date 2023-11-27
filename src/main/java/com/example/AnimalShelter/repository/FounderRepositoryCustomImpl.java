package com.example.AnimalShelter.repository;

import com.example.AnimalShelter.model.Founder;
import com.example.AnimalShelter.model.FounderAvgDTO;
import com.example.AnimalShelter.model.FounderIntDTO;
import com.example.AnimalShelter.model.Investment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.util.List;

public class FounderRepositoryCustomImpl implements FounderRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<FounderAvgDTO> find2() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<FounderAvgDTO> criteriaQuery = criteriaBuilder.createQuery(FounderAvgDTO.class);
        Root<Founder> founder = criteriaQuery.from(Founder.class);

        Join<Founder, Investment> investmentJoin = founder.join("investments", JoinType.INNER);
        Path<Double> amount = investmentJoin.get("investedMoney");
        Path<Long> founderId = founder.get("id");
        Path<String> founderName = founder.get("name");
        criteriaQuery.multiselect(
                founderId,
                founderName,
                criteriaBuilder.avg(amount).alias("avgInvestment")
        ).groupBy(founderId);

        criteriaQuery.orderBy(criteriaBuilder.desc(criteriaBuilder.avg(amount)));

        TypedQuery<FounderAvgDTO> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }

    public List<FounderIntDTO> find3() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<FounderIntDTO> criteriaQuery = criteriaBuilder.createQuery(FounderIntDTO.class);
        Root<Founder> founder = criteriaQuery.from(Founder.class);

        Join<Founder, Investment> investmentJoin = founder.join("investments", JoinType.INNER);
        Path<Integer> amount = investmentJoin.get("investedMoney");
        Path<Long> founderId = founder.get("id");
        Path<String> founderName = founder.get("name");
        criteriaQuery.multiselect(
                founderId,
                founderName,
                criteriaBuilder.max(amount).alias("maxInvestment")
        ).groupBy(founderId);

        criteriaQuery.orderBy(criteriaBuilder.desc(criteriaBuilder.max(amount)));

        TypedQuery<FounderIntDTO> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }
}
