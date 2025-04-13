package com.example.przewozy.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class PrzewozDTO {
    private LocalDate data;
    private LocalTime godzina;
}

