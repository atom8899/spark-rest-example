package com.fake_company.spark_rest_example.repository;

import com.fake_company.spark_rest_example.model.rate.MaterializedRate;
import com.fake_company.spark_rest_example.model.rate.Rate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.stream.Collectors;

public class RateH2Repository implements RateRepository {
    private final static RateH2Repository INSTANCE = new RateH2Repository();
    private final EntityManager em;


    private RateH2Repository() {
        var emf = Persistence.createEntityManagerFactory("com.fake_company.spark_rest_example");
        em = emf.createEntityManager();
    }

    public static RateH2Repository getInstance() {
        return INSTANCE;
    }

    @Override
    public Rate persistRate(final Rate rate) {
        em.getTransaction().begin();
        em.persist(rate);
        em.getTransaction().commit();
        return rate;
    }

    @Override
    public List<Rate> getRates() {
        return em.createNamedQuery("Rates.getAllRates", Rate.class).getResultList();
    }

    public List<MaterializedRate> getMaterializedRates() {
        return em.createNamedQuery("Rates.getAllRates", Rate.class).getResultList()
                .stream()
                .map(MaterializedRate::fromRate)
                .collect(Collectors.toList());
    }

    @Override
    public void clearExistingRates() {
        em.getTransaction().begin();
        em.createNamedQuery("Rates.removeExistingRates").executeUpdate();
        em.getTransaction().commit();
    }
}
