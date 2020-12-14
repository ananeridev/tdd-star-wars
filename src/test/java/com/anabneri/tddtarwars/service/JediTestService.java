package com.anabneri.tddtarwars.service;

import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.anabneri.tddtarwars.repository.JediRepository;
import com.anabneri.tddtarwars.services.JediService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.anabneri.tddtarwars.models.Jedi;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JediTestService {

    @Autowired
    private JediService service;

    @MockBean
    private JediRepository repository;

    @Test
    @DisplayName("Test findById Success")
    void testFindByIdSuccess() {

        Jedi mockJedi = new Jedi(1, "Jedi Name", 10, 1);
        doReturn(Optional.of(mockJedi)).when(repository).findById(1);

        Optional<Jedi> returnedJedi = service.findById(1);

        Assertions.assertTrue(returnedJedi.isPresent(), "Jedi was not found, tire-o do time das galaxias");
        Assertions.assertSame(returnedJedi.get(), mockJedi, "Jedis precisam ser o mesmo");
    }

    @Test
    @DisplayName("Test findById Not Found")
    void testFindByIdNotFound() {

        doReturn(Optional.empty()).when(repository).findById(1);

        Optional<Jedi> returnedJedi = service.findById(1);

        Assertions.assertFalse(returnedJedi.isPresent(), "Jedi nao fo encontrado como precisava ser");
    }

    @Test
    @DisplayName("Test findAll")
    void testFindAll() {

        Jedi mockJedi = new Jedi(1, "Jedi 1", 10, 1);
        Jedi mockJedi2 = new Jedi(2, "Jedi 2", 15, 3);
        doReturn(Arrays.asList(mockJedi, mockJedi2)).when(repository).findAll();

        // Execute the service call
        List<Jedi> jedis = service.findAll();

        Assertions.assertEquals(2, jedis.size(), "Jedis de mesma forca encontrados");
    }

}