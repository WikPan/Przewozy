package com.example.przewozy.repo;

import com.example.przewozy.entity.Bilet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BiletRepository extends JpaRepository<Bilet, Integer>{
    List<Bilet> findByKlientId(Integer id);
}
