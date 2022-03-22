package com.anabneri.tddtarwars.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Profile("test")
public class JediRepositoryTestConfiguration {

    @Primary
    @Bean
    public DataSource dataSource() {

        //setup satasource para os testes
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return dataSource;
    }
}
