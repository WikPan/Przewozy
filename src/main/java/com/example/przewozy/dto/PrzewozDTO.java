package com.example.przewozy.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class PrzewozDTO {
    private LocalDate date;
    private LocalTime time;
}

