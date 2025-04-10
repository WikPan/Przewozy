package com.example.przewozy.service;

import com.example.przewozy.entity.Przewoz;

import java.util.List;

public interface PrzewozService {

    Przewoz getPrzewoz(int i);
    List<Przewoz> getAll();
}
