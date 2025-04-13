package com.example.przewozy.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class PrzewozDTO {
    private final LocalDate date;
    private final LocalTime time;

    public PrzewozDTO(LocalDate date, LocalTime number) {
        this.date = date;
        this.time = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}

