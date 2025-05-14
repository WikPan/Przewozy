package com.example.przewozy.service;

import com.example.przewozy.entity.Autobus;
import com.example.przewozy.entity.Przewoz;

import java.util.List;

public interface AutobusService {
    List<Autobus> getAllAutobusy();
    Autobus getAutobusById(Integer id);
    Autobus createAutobus(Autobus autobus);
    Autobus updateAutobus(Integer id, Autobus updated);
    void deleteAutobus(Integer id);
    List<Przewoz> getPrzewozyForAutobus(Integer id);
}
