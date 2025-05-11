package com.example.przewozy;

import com.example.przewozy.dto.KlientDTO;
import com.example.przewozy.repo.KlientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class KlientRestIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private KlientRepository klientRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateAndFetchKlient() throws Exception {
        KlientDTO dto = new KlientDTO("Tomek","Malinowski","123123123","tomek@test.pl");

        // when
        mockMvc.perform(post("/klienci")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk());

        // then
        mockMvc.perform(get("/klienci"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].imie").value("Tomek"));
    }
}
