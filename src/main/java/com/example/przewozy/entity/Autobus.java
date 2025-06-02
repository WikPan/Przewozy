package com.example.przewozy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Autobus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Marka nie może być pusta")
    private String marka;
    @NotBlank(message = "Model nie może być pusty")
    private String model;
    @Positive(message = "Liczba miejsc musi być większa od zera")
    private int liczbaMiejsc;
    @Min(value = 1950, message = "Rok produkcji musi być >= 1950")
    private int rokProdukcji;

    @OneToMany(mappedBy = "autobus")
    @JsonIgnore
    private List<Przewoz> przewozy;
}
