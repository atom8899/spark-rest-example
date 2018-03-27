package com.fake_company.spark_rest_example.repository;

import com.fake_company.spark_rest_example.configuration.ApplicationConfiguration;
import com.fake_company.spark_rest_example.model.Rate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.List;

public class ParkingH2Repository implements ParkingRepository {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.fake_company.spark_rest_example");
    private EntityManager em;

    public ParkingH2Repository(final ApplicationConfiguration applicationConfiguration) throws SQLException {
        em = emf.createEntityManager();
    }

    public void init() throws SQLException {
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
}
