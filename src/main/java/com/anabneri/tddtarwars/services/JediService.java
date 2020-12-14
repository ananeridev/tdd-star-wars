package com.anabneri.tddtarwars.services;


import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.anabneri.tddtarwars.models.Jedi;
import com.anabneri.tddtarwars.repository.JediRepository;


@Service
public class JediService {

    private static final Logger logger = LogManager.getLogger(JediService.class);

    private final JediRepository jediRepository;

    public JediService(JediRepository jediRepository) {
        this.jediRepository = jediRepository;
    }

    public Optional<Jedi> findById(Integer id) {
        logger.info("Find Jedi with id: {}", id);
        return jediRepository.findById(id);
    }

    public List<Jedi> findAll() {
        logger.info("Find all Jedis on Galaxy");
        return jediRepository.findAll();
    }

    public Jedi save(Jedi jedi) {
        jedi.setVersion(1);

        logger.info("Save Jedi to the database: {}", jedi);
        return jediRepository.save(jedi);
    }

    public boolean update(Jedi jedi) {
        boolean updated = false;

        logger.info("Update jedi to the database: {}", jedi);

        Jedi savedProduct = this.save(jedi);

        if(savedProduct != null) updated = true;

        return updated;
    }

    public boolean delete(Integer id) {
        return true;
    }
}