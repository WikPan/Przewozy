package com.example.przewozy.service;

import com.example.przewozy.dto.PrzewozDTO;
import com.example.przewozy.entity.Przewoz;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface PrzewozService {

    Przewoz findPrzewoz(int i);
    List<Przewoz> findAll();

    //void updatePrzewoz(LocalDate date, LocalTime time);

    //void deletePrzewoz(int index);

    void createPrzewoz(PrzewozDTO przewozDTO);
}
