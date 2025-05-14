package com.example.przewozy;

import com.example.przewozy.entity.Autobus;
import com.example.przewozy.entity.Przewoz;
import com.example.przewozy.rest.AutobusController;
import com.example.przewozy.service.AutobusService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(controllers = AutobusController.class)
@Import({ 
    com.example.przewozy.assembler.AutobusModelAssembler.class,
    com.example.przewozy.exception.GlobalExceptionHandler.class 
})
class AutobusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AutobusService autobusService;

    @Test
    void shouldReturnListOfAutobusy() throws Exception {
        // przygotowanie dwóch encji Autobus
        Autobus a1 = new Autobus();
        a1.setId(1);
        a1.setMarka("Mercedes");
        a1.setModel("Sprinter");
        a1.setLiczbaMiejsc(30);
        a1.setRokProdukcji(2018);

        Autobus a2 = new Autobus();
        a2.setId(2);
        a2.setMarka("MAN");
        a2.setModel("Lion");
        a2.setLiczbaMiejsc(50);
        a2.setRokProdukcji(2020);

        // stub serwisu
        given(autobusService.getAllAutobusy()).willReturn(List.of(a1, a2));

        mockMvc.perform(get("/autobusy")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // sprawdź, że mamy embedowany listę DTO
                .andExpect(jsonPath("$._embedded.autobusDTOList").exists())
                .andExpect(jsonPath("$._embedded.autobusDTOList.length()").value(2))
                // i że jest link self na kolekcji
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void shouldReturnPrzewozyForAutobus() throws Exception {
        // najpierw encja Autobus, żeby móc ją wskazać w Przewoz
        Autobus parent = new Autobus();
        parent.setId(1);
        parent.setMarka("Test");
        parent.setModel("X");
        parent.setLiczbaMiejsc(10);
        parent.setRokProdukcji(2021);

        // przygotowanie dwóch encji Przewoz
        Przewoz p1 = new Przewoz();
        p1.setId(100);
        p1.setData(LocalDate.of(2024, 6, 1));
        p1.setGodzina(LocalTime.of(12, 0));
        p1.setAutobus(parent);
        // trasa można zostawić null lub dorzucić fikcyjną, nie wpływa na długość listy

        Przewoz p2 = new Przewoz();
        p2.setId(101);
        p2.setData(LocalDate.of(2024, 6, 2));
        p2.setGodzina(LocalTime.of(15, 30));
        p2.setAutobus(parent);

        // stub serwisu
        given(autobusService.getPrzewozyForAutobus(1)).willReturn(List.of(p1, p2));

        mockMvc.perform(get("/autobusy/1/przewozy")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // sprawdź, że mamy embedowany przewozDTOList
                .andExpect(jsonPath("$._embedded.przewozDTOList").exists())
                .andExpect(jsonPath("$._embedded.przewozDTOList.length()").value(2))
                // oraz link self na tej kolekcji
                .andExpect(jsonPath("$._links.self.href").value(org.hamcrest.Matchers.endsWith("/autobusy/1/przewozy")));
    }
}
