package com.example.przewozy;

import com.example.przewozy.dto.KlientDTO;
import com.example.przewozy.rest.KlientRestController;
import com.example.przewozy.service.KlientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(KlientRestController.class)
class KlientRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KlientService klientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAll() throws Exception {
        KlientDTO dto = new KlientDTO("Jan", "Kowalski", "123456789", "jan@kowalski.pl");
        when(klientService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/klienci"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].imie").value("Jan"));
    }

    @Test
    void testCreate() throws Exception {
        KlientDTO dto = new KlientDTO("Anna", "Nowak", "987654321", "anna@nowak.pl");
        when(klientService.create(any())).thenReturn(new KlientDTO(1, "Anna","Nowak","987654321","anna@nowak.pl"));

        mockMvc.perform(post("/klienci")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.imie").value("Anna"));

        verify(klientService, times(1)).create(any(KlientDTO.class));
    }

    @Test
    void testUpdate() throws Exception {
        Integer id = 1;
        KlientDTO dto = new KlientDTO("Edyta","Zmiana","111222333","edyta@zmiana.pl");
        when(klientService.update(eq(id), any()))
            .thenReturn(new KlientDTO(id,"Edyta","Zmiana","111222333","edyta@zmiana.pl"));

        mockMvc.perform(put("/klienci/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.imie").value("Edyta"));

        verify(klientService, times(1)).update(eq(id), any(KlientDTO.class));
    }

    @Test
    void testDelete_shouldReturnOk_whenClientDeleted() throws Exception {
        when(klientService.delete(1)).thenReturn(true);

        mockMvc.perform(delete("/klienci/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Klient został usunięty."));

        verify(klientService, times(1)).delete(1);
    }

}
