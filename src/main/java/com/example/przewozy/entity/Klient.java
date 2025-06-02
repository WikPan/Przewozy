package com.example.przewozy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "bilety")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Klient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String imie;
    private String nazwisko;
    private String telefon;
    private String email;

    @OneToMany(mappedBy = "klient")
    private List<Bilet> bilety;
}
