package com.example.przewozy.entity;

import com.example.przewozy.enums.StatusBiletu;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bilet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "klient_id")
    private Klient klient;

    @ManyToOne
    @JoinColumn(name = "przewoz_id")
    private Przewoz przewoz;

    private String miejsce;
    private Integer cena;

    @Enumerated(EnumType.STRING)
    private StatusBiletu status;
}