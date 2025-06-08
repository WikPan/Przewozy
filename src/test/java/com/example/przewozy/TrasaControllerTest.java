package com.example.przewozy;

import com.example.przewozy.entity.Trasa;
import com.example.przewozy.repo.TrasaRepository;
import com.example.przewozy.repo.PrzewozRepository;
import com.example.przewozy.entity.Przewoz;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TrasaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TrasaRepository trasaRepository;

    @Autowired
    private PrzewozRepository przewozRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Trasa testTrasa;

    @BeforeEach
    void setup() {
        przewozRepository.deleteAll();
        trasaRepository.deleteAll();

        testTrasa = new Trasa();
        testTrasa.setPunktStartowy("Kraków");
        testTrasa.setPunktDocelowy("Warszawa");
        testTrasa.setDystansKm(300.0);
        trasaRepository.save(testTrasa);
    }

    @Test
    void getAllTrasy_shouldReturnListWithLinks() throws Exception {
        mockMvc.perform(get("/trasy"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.trasaDTOList[0].punktStartowy", is("Kraków")))
            .andExpect(jsonPath("$._embedded.trasaDTOList[0]._links.przewozy.href").exists());
    }

    @Test
    void getSingleTrasa_shouldReturnCorrectTrasa() throws Exception {
        mockMvc.perform(get("/trasy/" + testTrasa.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.punktStartowy", is("Kraków")))
            .andExpect(jsonPath("$._links.przewozy.href").exists());
    }

    @Test
    void createTrasa_shouldReturnConfirmationMessage() throws Exception {
        Trasa newTrasa = new Trasa();
        newTrasa.setPunktStartowy("Poznań");
        newTrasa.setPunktDocelowy("Gdańsk");
        newTrasa.setDystansKm(320);

        mockMvc.perform(post("/trasy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTrasa)))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Dodano trasę")));
    }

    @Test
    void updateTrasa_shouldReturnUpdateConfirmation() throws Exception {
        Trasa updated = new Trasa();
        updated.setPunktStartowy("Kraków");
        updated.setPunktDocelowy("Gdynia");
        updated.setDystansKm(500.0);

        mockMvc.perform(put("/trasy/" + testTrasa.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Zaktualizowano trasę")));
    }

    @Test
    void deleteTrasa_shouldReturnDeleteConfirmation() throws Exception {
        mockMvc.perform(delete("/trasy/" + testTrasa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Usunięto trasę")));
    }

    @Test
    void getTrasaWithInvalidId_shouldReturn404() throws Exception {
        mockMvc.perform(get("/trasy/99999"))
            .andExpect(status().isNotFound());
    }



}
