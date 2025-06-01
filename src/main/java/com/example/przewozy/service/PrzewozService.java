package com.example.przewozy.service;

import com.example.przewozy.dto.PrzewozDTO;
import com.example.przewozy.entity.Autobus;
import com.example.przewozy.entity.Przewoz;
import com.example.przewozy.entity.Trasa;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface PrzewozService {
    //void generateData();

    CollectionModel<PrzewozDTO> getPrzewozy();

    CollectionModel<PrzewozDTO> getPrzewozyByParams(Integer trasaId, Integer autobusId);
    
    PrzewozDTO getPrzewoz(Integer id);

    ResponseEntity<?> createPrzewoz(PrzewozDTO dto);

    Autobus getAutobusForPrzewoz(Integer id);

    Trasa getTrasaForPrzewoz(Integer id);

    ResponseEntity<?> updatePrzewoz(Integer id, Przewoz updatedPrzewoz);

    ResponseEntity<?> deletePrzewoz(Integer id);
}
