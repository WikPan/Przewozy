package com.example.przewozy.dto;

import com.example.przewozy.entity.Autobus;
import com.example.przewozy.entity.Trasa;
import com.example.przewozy.validation.ExistsInDatabase;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CreatePrzewozDTO {
    @NotNull
    private LocalDate data;
    @NotNull
    private LocalTime godzina;
    @NotNull
    @ExistsInDatabase(entity = Autobus.class)
    private Integer autobusId;
    @NotNull
    @ExistsInDatabase(entity = Trasa.class)
    private Integer trasaId;
}

