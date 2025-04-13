package com.example.przewozy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Bilet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String miejsce;
    private Integer cena;
    private Boolean Status;

    @ManyToOne
    @JoinColumn(name = "przewoz_id")
    private Przewoz przewoz;

    @ManyToOne
    @JoinColumn(name = "klient_id")
    private Klient klient;
}