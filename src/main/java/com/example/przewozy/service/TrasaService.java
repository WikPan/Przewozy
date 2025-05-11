package com.example.przewozy.service;

import com.example.przewozy.dto.PrzewozDTO;
import com.example.przewozy.dto.TrasaDTO;
import com.example.przewozy.entity.Trasa;
import org.springframework.hateoas.CollectionModel;

public interface TrasaService {
    CollectionModel<TrasaDTO> findAll();
    TrasaDTO findById(Integer id);
    String create(Trasa trasa);
    CollectionModel<PrzewozDTO> getPrzewozyForTrasa(Integer id);
    String update(Integer id, Trasa trasa);
    String delete(Integer id);
}
