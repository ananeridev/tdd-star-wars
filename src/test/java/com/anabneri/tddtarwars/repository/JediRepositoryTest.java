package com.anabneri.tddtarwars.repository;

import javax.sql.DataSource;

import com.anabneri.tddtarwars.models.Jedi;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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

import java.util.List;
import java.util.Optional;

@ExtendWith({DBUnitExtension.class, SpringExtension.class})
@SpringBootTest
@ActiveProfiles("test")
public class JediRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JediRepository jediRepository;

//    public ConnectionHolder getConnectionHolder() {
//        // retorna uma funcao que recupera a conexa com o data source
//        return () -> dataSource.getConnection();
//    }

    @Test
    @DataSet("jedi.yml")
    public void testFindAll() {
        List<Jedi> jedis = jediRepository.findAll();
        Assertions.assertEquals(2, jedis.size(), "We should have 2 jedis in our database");
    }

    @Test
    @DataSet("jedi.yml")
    public void testFindByIdSuccess() {
        Optional<Jedi> jedi = jediRepository.findById(2);

        Assertions.assertTrue(jedi.isPresent(), "Jedi with id 2 should be found");

        Jedi j = jedi.get();
        Assertions.assertEquals(2, j.getId().intValue(), "Jedi id should be 2");
        Assertions.assertEquals("HanSolo", j.getName(), "Jedi name should \"Hansolo\" ");
        Assertions.assertEquals(2, j.getVersion().intValue(), "Jedi version should be 2");
    }


}
