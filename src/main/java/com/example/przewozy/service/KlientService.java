package com.example.przewozy.service;

import com.example.przewozy.dto.KlientDTO;

import java.util.List;

public interface KlientService {
    List<KlientDTO> findAll();
    KlientDTO findById(Long id);
    KlientDTO create(KlientDTO dto);
    KlientDTO update(Long id, KlientDTO dto);
    void delete(Long id);
}
