package com.example.przewozy.rest;

import com.example.przewozy.dto.BiletDTO;
import com.example.przewozy.entity.*;
import com.example.przewozy.enums.StatusBiletu;
import com.example.przewozy.repo.*;
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
class BiletControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private BiletRepository biletRepo;
    @Autowired private KlientRepository klientRepo;
    @Autowired private PrzewozRepository przewozRepo;
    @Autowired private AutobusRepository autobusRepo;
    @Autowired private TrasaRepository trasaRepo;
    @Autowired private ObjectMapper objectMapper;

    private Klient testKlient;
    private Autobus testAutobus;
    private Trasa testTrasa;
    private Przewoz testPrzewoz;
    private Bilet testBilet;

    @BeforeEach
    void setup() {
        // Czyścimy repozytoria
        biletRepo.deleteAll();
        klientRepo.deleteAll();
        przewozRepo.deleteAll();
        autobusRepo.deleteAll();
        trasaRepo.deleteAll();

        // Tworzymy klienta
        testKlient = new Klient();
        testKlient.setImie("Jan");
        testKlient.setNazwisko("Kowalski");
        testKlient.setTelefon("123456789");
        testKlient.setEmail("jan.kowalski@example.com");
        klientRepo.save(testKlient);

        // Tworzymy autobus
        testAutobus = new Autobus();
        testAutobus.setMarka("Volvo");
        testAutobus.setModel("7900");
        testAutobus.setLiczbaMiejsc(50);
        testAutobus.setRokProdukcji(2018);
        autobusRepo.save(testAutobus);

        // Tworzymy trasę
        testTrasa = new Trasa();
        testTrasa.setPunktStartowy("Poznań");
        testTrasa.setPunktDocelowy("Gdańsk");
        testTrasa.setDystansKm(300.0);
        trasaRepo.save(testTrasa);

        // Tworzymy przewóz
        testPrzewoz = new Przewoz();
        testPrzewoz.setData(LocalDate.of(2025,5,25));
        testPrzewoz.setGodzina(LocalTime.of(14,0));
        testPrzewoz.setAutobus(testAutobus);
        testPrzewoz.setTrasa(testTrasa);
        przewozRepo.save(testPrzewoz);

        // Tworzymy bilet
        testBilet = new Bilet();
        testBilet.setKlient(testKlient);
        testBilet.setPrzewoz(testPrzewoz);
        testBilet.setMiejsce("12A");
        testBilet.setCena(100);
        testBilet.setStatus(StatusBiletu.AKTYWNY);
        biletRepo.save(testBilet);
    }

    @Test
    void getAllBilety_shouldReturnListWithLinks() throws Exception {
        mockMvc.perform(get("/bilety"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.biletDTOList[0].miejsce", is("12A")))
            .andExpect(jsonPath("$._embedded.biletDTOList[0]._links.self.href").exists())
            .andExpect(jsonPath("$._embedded.biletDTOList[0]._links.klient.href").exists())
            .andExpect(jsonPath("$._embedded.biletDTOList[0]._links.przewoz.href").exists());
    }

    @Test
    void getSingleBilet_shouldReturnCorrectBilet() throws Exception {
        mockMvc.perform(get("/bilety/" + testBilet.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.miejsce", is("12A")))
            .andExpect(jsonPath("$.status", is("AKTYWNY")))
            .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void createBilet_shouldReturnCreatedDTO() throws Exception {
        BiletDTO dto = new BiletDTO(
            testKlient.getId(),
            testPrzewoz.getId(),
            "15B",
            120,
            StatusBiletu.ANULOWANY
        );

        mockMvc.perform(post("/bilety")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.miejsce", is("15B")))
            .andExpect(jsonPath("$.status", is("ANULOWANY")))
            .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void updateBilet_shouldReturnUpdatedDTO() throws Exception {
        BiletDTO dto = new BiletDTO(
            testKlient.getId(),
            testPrzewoz.getId(),
            "12A",
            150,
            StatusBiletu.NIEAKTYWNY
        );

        mockMvc.perform(put("/bilety/" + testBilet.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.cena", is(150)))
            .andExpect(jsonPath("$.status", is("NIEAKTYWNY")));
    }

    @Test
    void deleteBilet_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/bilety/" + testBilet.getId()))
            .andExpect(status().isNoContent());
    }

    @Test
    void getKlientForBilet_shouldReturnKlient() throws Exception {
        mockMvc.perform(get("/bilety/" + testBilet.getId() + "/klient"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.imie", is("Jan")))
            .andExpect(jsonPath("$.nazwisko", is("Kowalski")));
    }

    @Test
    void getPrzewozForBilet_shouldReturnPrzewoz() throws Exception {
        mockMvc.perform(get("/bilety/" + testBilet.getId() + "/przewoz"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data", is("2025-05-25")))
            .andExpect(jsonPath("$.godzina", is("14:00:00")));
    }

    @Test
    void getBiletWithInvalidId_shouldReturn404() throws Exception {
        mockMvc.perform(get("/bilety/99999"))
            .andExpect(status().isNotFound());
    }

    @Test
    void createBilet_withMissingFields_shouldReturn400() throws Exception {
        BiletDTO bad = new BiletDTO();

        mockMvc.perform(post("/bilety")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bad)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors.klientId").exists())
            .andExpect(jsonPath("$.errors.przewozId").exists())
            .andExpect(jsonPath("$.errors.miejsce").exists())
            .andExpect(jsonPath("$.errors.cena").exists())
            .andExpect(jsonPath("$.errors.status").exists());
    }

}
