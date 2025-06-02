package com.example.przewozy.service;

import com.example.przewozy.dto.BiletDTO;
import com.example.przewozy.dto.KlientDTO;
import com.example.przewozy.dto.PrzewozDTO;
import com.example.przewozy.entity.Bilet;
import com.example.przewozy.enums.StatusBiletu;

import java.util.List;

public interface BiletService {
    /** Używane przez kontroler + assembler */
    List<Bilet> getAllEncje();

    /** CRUD i relacje rzucają 404 przez ResourceNotFoundException */
    BiletDTO getById(Integer id);
    BiletDTO create(BiletDTO dto);
    BiletDTO update(Integer id, BiletDTO dto);
    BiletDTO changeBiletStatus(Integer id, StatusBiletu status);
    void delete(Integer id);
    KlientDTO getKlientForBilet(Integer id);
    PrzewozDTO getPrzewozForBilet(Integer id);
}
