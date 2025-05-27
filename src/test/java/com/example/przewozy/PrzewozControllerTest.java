package com.example.przewozy.rest;

import com.example.przewozy.dto.PrzewozDTO;
import com.example.przewozy.entity.Autobus;
import com.example.przewozy.entity.Przewoz;
import com.example.przewozy.entity.Trasa;
import com.example.przewozy.repo.AutobusRepository;
import com.example.przewozy.repo.PrzewozRepository;
import com.example.przewozy.repo.TrasaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PrzewozControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private PrzewozRepository przewozRepo;
    @Autowired private AutobusRepository autobusRepo;
    @Autowired private TrasaRepository trasaRepo;
    @Autowired private ObjectMapper objectMapper;

    private Autobus testAutobus;
    private Trasa testTrasa;
    private Przewoz testPrzewoz;

    @BeforeEach
    void setup() {
        // Czyścimy dane, nie chcemy danych z generateData()
        przewozRepo.deleteAll();
        autobusRepo.deleteAll();
        trasaRepo.deleteAll();

        // Tworzymy autobus
        testAutobus = new Autobus();
        testAutobus.setMarka("TestMarka");
        testAutobus.setModel("TestModel");
        testAutobus.setLiczbaMiejsc(50);
        testAutobus.setRokProdukcji(2010);
        autobusRepo.save(testAutobus);

        // Tworzymy trasę
        testTrasa = new Trasa();
        testTrasa.setPunktStartowy("Kraków");
        testTrasa.setPunktDocelowy("Warszawa");
        testTrasa.setDystansKm(300.0);
        trasaRepo.save(testTrasa);

        // Tworzymy przewóz powiązany z powyższymi
        testPrzewoz = new Przewoz();
        testPrzewoz.setData(LocalDate.of(2025,5,25));
        testPrzewoz.setGodzina(LocalTime.of(12,30));
        testPrzewoz.setAutobus(testAutobus);
        testPrzewoz.setTrasa(testTrasa);
        przewozRepo.save(testPrzewoz);
    }

    @Test
    void getAllPrzewozy_shouldReturnListWithLinks() throws Exception {
        mockMvc.perform(get("/przewozy"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.przewozDTOList[0].data", is("2025-05-25")))
            .andExpect(jsonPath("$._embedded.przewozDTOList[0]._links.self.href").exists())
            .andExpect(jsonPath("$._embedded.przewozDTOList[0]._links.autobus.href").exists())
            .andExpect(jsonPath("$._embedded.przewozDTOList[0]._links.trasa.href").exists());
    }

    @Test
    void getSinglePrzewoz_shouldReturnCorrectPrzewoz() throws Exception {
        mockMvc.perform(get("/przewozy/" + testPrzewoz.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data", is("2025-05-25")))
            .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void createPrzewoz_shouldReturnConfirmationMessage() throws Exception {
        PrzewozDTO dto = new PrzewozDTO();
        dto.setData(LocalDate.of(2025,6,1));
        dto.setGodzina(LocalTime.of(8,0));
        dto.setAutobusId(testAutobus.getId());
        dto.setTrasaId(testTrasa.getId());

        mockMvc.perform(post("/przewozy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Dodano przewóz")));
    }

    @Test
    void updatePrzewoz_shouldReturnUpdateConfirmation() throws Exception {
        Przewoz updated = new Przewoz();
        updated.setData(LocalDate.of(2025,6,2));
        updated.setGodzina(LocalTime.of(9,15));
        updated.setAutobus(testAutobus);
        updated.setTrasa(testTrasa);

        mockMvc.perform(put("/przewozy/" + testPrzewoz.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Zaktualizowano przewóz")));
    }

    @Test
    void deletePrzewoz_shouldReturnDeleteConfirmation() throws Exception {
        mockMvc.perform(delete("/przewozy/" + testPrzewoz.getId()))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Usunięto przewóz")));
    }

    @Test
    void getAutobusForPrzewoz_shouldReturnAutobus() throws Exception {
        mockMvc.perform(get("/przewozy/" + testPrzewoz.getId() + "/autobus"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.marka", is("TestMarka")))
            .andExpect(jsonPath("$.model", is("TestModel")));
    }

    @Test
    void getTrasaForPrzewoz_shouldReturnTrasa() throws Exception {
        mockMvc.perform(get("/przewozy/" + testPrzewoz.getId() + "/trasa"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.punktStartowy", is("Kraków")))
            .andExpect(jsonPath("$.punktDocelowy", is("Warszawa")))
            .andExpect(jsonPath("$.dystansKm", is(300.0)));
    }


    @Test
    void getPrzewozWithInvalidId_shouldReturn404() throws Exception {
        mockMvc.perform(get("/przewozy/99999"))
            .andExpect(status().isNotFound());
    }

    @Test
    void createPrzewoz_withMissingFields_shouldReturn400() throws Exception {
        PrzewozDTO bad = new PrzewozDTO();
        bad.setData(null);
        bad.setGodzina(null);

        mockMvc.perform(post("/przewozy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bad)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors.data").exists())
            .andExpect(jsonPath("$.errors.godzina").exists())
            .andExpect(jsonPath("$.errors.autobusId").exists())
            .andExpect(jsonPath("$.errors.trasaId").exists());
    }

}
