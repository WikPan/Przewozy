package com.example.przewozy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Przewoz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;
    private LocalTime godzina;

    @ManyToOne
    @JoinColumn(name = "klient_id")
    private Klient klient;

    @ManyToOne
    @JoinColumn(name = "autobus_id")
    private Autobusy autobus;

    @ManyToOne
    @JoinColumn(name = "trasa_id")
    private Trasa trasa;


    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getGodzina() {
        return godzina;
    }

    public void setGodzina(LocalTime godzina) {
        this.godzina = godzina;
    }
}
