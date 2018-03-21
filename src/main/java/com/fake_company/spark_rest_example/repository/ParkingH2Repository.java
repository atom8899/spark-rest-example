package com.fake_company.spark_rest_example.repository;

import com.fake_company.spark_rest_example.configuration.ApplicationConfiguration;
import com.fake_company.spark_rest_example.model.Rates;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class ParkingH2Repository implements ParkingRepository {

    private final DataSource dataSource;

    public ParkingH2Repository(final ApplicationConfiguration applicationConfiguration) {
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
        config.setConnectionTestQuery("VALUES 1");
        config.addDataSourceProperty("URL", applicationConfiguration.getConnection().getUrl());
        config.addDataSourceProperty("user", applicationConfiguration.getConnection().getUsername());
        config.addDataSourceProperty("password", applicationConfiguration.getConnection().getPassword());
        this.dataSource = new HikariDataSource(config);
    }

    @Override
    public Rates persistRate(Rates rate) {
        return null;
    }
}
