package com.example.przewozy.service;

import com.example.przewozy.dto.BiletDTO;
import com.example.przewozy.dto.KlientDTO;
import com.example.przewozy.dto.PrzewozDTO;

import java.util.List;

public interface BiletService {
    List<BiletDTO> getAll();
    BiletDTO getById(Long id);
    BiletDTO create(BiletDTO dto);
    BiletDTO update(Long id, BiletDTO dto);
    void delete(Long id);
    KlientDTO getKlientForBilet(Long id);
    PrzewozDTO getPrzewozForBilet(Long id);
}
