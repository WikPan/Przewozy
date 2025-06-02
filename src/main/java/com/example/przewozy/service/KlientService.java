package com.example.przewozy.service;

import com.example.przewozy.dto.KlientDTO;
import com.example.przewozy.entity.Bilet;

import java.util.List;

public interface KlientService {
    List<KlientDTO> findAll();
    KlientDTO findById(Integer id);
    KlientDTO create(KlientDTO dto);
    KlientDTO update(Integer id, KlientDTO dto);
    boolean delete(Integer id);
    List<Bilet> getBiletyForKlient(Integer id);
}
