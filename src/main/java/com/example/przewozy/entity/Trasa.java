package com.example.przewozy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "przewozy")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Trasa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 

    @NotBlank
    private String punktStartowy;

    @NotBlank
    private String punktDocelowy;

    @Min(1)
    private double dystansKm;

    @OneToMany(mappedBy = "trasa")
    @JsonIgnore
    private List<Przewoz> przewozy;
}
