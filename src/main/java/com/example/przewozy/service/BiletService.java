package com.example.przewozy.service;

import com.example.przewozy.dto.BiletDTO;
import com.example.przewozy.dto.KlientDTO;
import com.example.przewozy.dto.PrzewozDTO;
import com.example.przewozy.entity.Bilet;

import java.util.List;

public interface BiletService {
    /** Używane przez kontroler + assembler */
    List<Bilet> getAllEncje();

    /** CRUD i relacje rzucają 404 przez ResourceNotFoundException */
    BiletDTO getById(Long id);
    BiletDTO create(BiletDTO dto);
    BiletDTO update(Long id, BiletDTO dto);
    void delete(Long id);
    KlientDTO getKlientForBilet(Long id);
    PrzewozDTO getPrzewozForBilet(Long id);
}
