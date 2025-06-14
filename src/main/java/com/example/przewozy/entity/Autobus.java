package com.example.przewozy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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

    private String marka;
    private String model;
    private int liczbaMiejsc;
    private int rokProdukcji;

    @OneToMany(mappedBy = "autobus")
    @JsonIgnore
    private List<Przewoz> przewozy;
}
