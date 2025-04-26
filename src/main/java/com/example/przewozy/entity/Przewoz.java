package com.example.przewozy.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Przewoz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private LocalDate data;
    private LocalTime godzina;

    @OneToMany(mappedBy = "przewoz")
    private List<Bilet> bilet;

    @ManyToOne
    @JoinColumn(name = "autobus_id")
    private Autobus autobus;

    @ManyToOne
    @JoinColumn(name = "trasa_id")
    private Trasa trasa;
}
