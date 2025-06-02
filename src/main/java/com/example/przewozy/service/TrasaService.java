package com.example.przewozy.service;

import com.example.przewozy.entity.Przewoz;
import com.example.przewozy.entity.Trasa;

import java.util.List;

public interface TrasaService {
    List<Trasa> findAll();
    Trasa findById(Integer id);
    Trasa create(Trasa trasa);
    Trasa update(Integer id, Trasa trasa);
    void delete(Integer id);
    List<Przewoz> getPrzewozyForTrasa(Integer id);
}
