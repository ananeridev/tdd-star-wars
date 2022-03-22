package com.anabneri.tddtarwars.repository;

import javax.sql.DataSource;

import com.github.database.rider.junit5.DBUnitExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({DBUnitExtension.class, SpringExtension.class})
@SpringBootTest
@ActiveProfiles("test")
public class JediRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JediRepository jediRepository;

    public ConnectionHolder getConnectionHolder() {
        // retorna uma funcao que tenta a conexa com o data source
        return () -> dataSource.getConnection();
    }

}
