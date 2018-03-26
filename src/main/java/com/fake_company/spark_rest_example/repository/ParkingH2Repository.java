package com.fake_company.spark_rest_example.repository;

import com.fake_company.spark_rest_example.configuration.ApplicationConfiguration;
import com.fake_company.spark_rest_example.model.Rates;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ParkingH2Repository implements ParkingRepository {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.fake_company.spark_rest_example");
    private EntityManager em;

    public ParkingH2Repository(final ApplicationConfiguration applicationConfiguration) throws SQLException {
        em = emf.createEntityManager();
    }

    public void init() throws SQLException {
//        final Connection connection = dataSource.getConnection();
//        connection.setAutoCommit(false);
//        connection.prepareStatement("CREATE SCHEMA parking_example;\n" +
//                "      CREATE TABLE parking_example.rates(\n" +
//                "        days varchar(255),\n" +
//                "        times varchar(255),\n" +
//                "        rate int\n" +
//                "      );").execute();
    }

    @Override
    public Rates persistRate(Rates rate) {
        return null;
    }
}
