package com.example.przewozy.service;

import com.example.przewozy.dto.KlientDTO;
import com.example.przewozy.entity.Klient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KlientService {
    List<Klient> findAll();
    Klient findById(Long id);
    void create(KlientDTO dto);
    void update(Long id, KlientDTO dto);
    void delete(Long id);

    Page<Klient> findAllPaged(Pageable pageable);
}
