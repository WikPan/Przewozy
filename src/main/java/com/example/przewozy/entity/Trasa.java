package com.example.przewozy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trasa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String punktStartowy;
    private String punktDocelowy;
    private double dystansKm;

    @OneToMany(mappedBy = "trasa")
    private List<Przewoz> przewozy;
}
