package com.anabneri.tddtarwars.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.anabneri.tddtarwars.models.Jedi;

@Repository
public class JediRepositoryImpl implements JediRepository {

    private static final Logger logger = LogManager.getLogger(JediRepositoryImpl.class);

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    // construtor para identificar o jdbc template
    public JediRepositoryImpl(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;

        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("jedis")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<Jedi> findById(int id) {
        try {
            Jedi jedi = jdbcTemplate.queryForObject("SELECT * FROM jedis WHERE id = ?",
                    new Object[]{id},
                    (rs, rowNum) -> {
                        Jedi p = new Jedi();
                        p.setId(rs.getInt("id"));
                        p.setName(rs.getString("name"));
                        p.setStrenght(rs.getInt("strenght"));
                        p.setVersion(rs.getInt("version"));
                        return p;
                    });
            return Optional.of(jedi);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Jedi> findAll() {
        return jdbcTemplate.query("SELECT * FROM jedis",
                (rs, rowNumber) -> {
                    Jedi jedi = new Jedi();
                    jedi.setId(rs.getInt("id"));
                    jedi.setName(rs.getString("name"));
                    jedi.setStrenght(rs.getInt("strenght"));
                    jedi.setVersion(rs.getInt("version"));
                    return jedi;
                });
    }


    @Override
    public Jedi save(Jedi jedi) {

        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("name", jedi.getName());
        parameters.put("strenght", jedi.getStrenght());
        parameters.put("version", jedi.getVersion());

        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);

        logger.info("Inserting jedi into database, generated key is: {}", newId);

        jedi.setId((Integer)newId);

        return jedi;
    }

    @Override
    public boolean update(Jedi jedi) {
        return jdbcTemplate.update("UPDATE jedis SET name = ?, strenght = ?, version = ? WHERE id = ?",
                jedi.getName(),
                jedi.getStrenght(),
                jedi.getVersion(),
                jedi.getId()) == 1;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM jedis WHERE id = ?", id) == 1;
    }
}
