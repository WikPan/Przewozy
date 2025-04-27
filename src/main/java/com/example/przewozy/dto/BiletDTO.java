package com.example.przewozy.dto;

import com.example.przewozy.enums.StatusBiletu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BiletDTO {
    private Long id;
    private Long klientId;
    private Integer przewozId;
    private String miejsce;
    private Integer cena;
    private StatusBiletu status;
}