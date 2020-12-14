package com.anabneri.tddtarwars.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Optional;

import com.anabneri.tddtarwars.services.JediService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.anabneri.tddtarwars.models.Jedi;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JediControllerTest {

    @MockBean
    private JediService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /jedi/1 - Found")
    void testGetJediByIdFound() throws Exception {

        Jedi mockJedi = new Jedi(1, "HanSolo", 1,1);
        doReturn(Optional.of(mockJedi)).when(service).findById(1);


        mockMvc.perform(get("/jedi/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(header().string(HttpHeaders.ETAG, "\"1\""))
                .andExpect(header().string(HttpHeaders.LOCATION, "/jedi/1"))

                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("HanSolo")))
                .andExpect(jsonPath("$.strenght", is(1)))
                .andExpect(jsonPath("$.version", is(1)));
    }

    @Test
    @DisplayName("GET /jedi/1 - Not Found")
    void testGetJediByIdNotFound() throws Exception {
        doReturn(Optional.empty()).when(service).findById(1);

        mockMvc.perform(get("/jedi/{id}", 1))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("POST /jedi - Success")
    void testCreateJedi() throws Exception {
        Jedi postJedi = new Jedi("Jedi Name", 1);
        Jedi mockJedi = new Jedi(1, "Jedi Name", 1, 1);
        doReturn(mockJedi).when(service).save(any());

        mockMvc.perform(post("/jedi")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postJedi)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(header().string(HttpHeaders.ETAG, "\"1\""))
                .andExpect(header().string(HttpHeaders.LOCATION, "/jedi/1"))

                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Jedi Name")))
                .andExpect(jsonPath("$.strenght", is(1)))
                .andExpect(jsonPath("$.version", is(1)));
    }

    @Test
    @DisplayName("PUT /jedi/1 - Success")
    void testUpdateJediSuccess() throws Exception {
        Jedi putJedi = new Jedi("Princesa Leia", 1);
        Jedi mockJedi = new Jedi(1, "Princesa Leia", 1, 1);

        doReturn(Optional.of(mockJedi)).when(service).findById(1);
        doReturn(true).when(service).update(any());

        mockMvc.perform(put("/jedi/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 1)
                .content(asJsonString(putJedi)))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(header().string(HttpHeaders.ETAG, "\"2\""))
                .andExpect(header().string(HttpHeaders.LOCATION, "/jedi/1"))

                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Princesa Leia")))
                .andExpect(jsonPath("$.strenght", is(1)))
                .andExpect(jsonPath("$.version", is(2)));
    }

    @Test
    @DisplayName("PUT /jedi/1 - Version Mismatch")
    void testJediPutVersionMismatch() throws Exception {

        Jedi putJedi = new Jedi("Luke Skywalker", 30);
        Jedi mockJedi = new Jedi(1, "Luke Skywalker", 30, 2);
        doReturn(Optional.of(mockJedi)).when(service).findById(1);
        doReturn(true).when(service).update(any());

        mockMvc.perform(put("/jedi/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 1)
                .content(asJsonString(putJedi)))

                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("PUT /jedi/1 - Not Found")
    void testJediPutNotFound() throws Exception {

        Jedi putJedi = new Jedi("Yoda", 100);
        doReturn(Optional.empty()).when(service).findById(1);

        mockMvc.perform(put("/jedi/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 1)
                .content(asJsonString(putJedi)))

                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /jedi/1 - Success")
    void testJediDeleteSuccess() throws Exception {

        Jedi mockJedi = new Jedi(1, "Jedi name", 10, 1);

        doReturn(Optional.of(mockJedi)).when(service).findById(1);
        doReturn(true).when(service).delete(1);

        mockMvc.perform(delete("/jedi/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /jedi/1 - Not Found")
    void testJediDeleteNotFound() throws Exception {

        doReturn(Optional.empty()).when(service).findById(1);

        mockMvc.perform(delete("/jedi/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /jedi/1 - Failure")
    void testJediDeleteFailure() throws Exception {

        Jedi mockJedi = new Jedi(1, "Jedi Name", 10, 1);

        doReturn(Optional.of(mockJedi)).when(service).findById(1);
        doReturn(false).when(service).delete(1);

        mockMvc.perform(delete("/jedi/{id}", 1))
                .andExpect(status().isInternalServerError());
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}