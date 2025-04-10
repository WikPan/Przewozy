package com.example.przewozy.service;

import com.example.przewozy.entity.Przewoz;
import com.example.przewozy.repo.PrzewozRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class PrzewozServiceImpl implements PrzewozService{

    private final PrzewozRepository przewozRepository;

    @Autowired
    public PrzewozServiceImpl(PrzewozRepository przewozRepository) {
        this.przewozRepository = przewozRepository;
    }

    @PostConstruct
    public void loadData() {
        // Ładowanie danych
        Przewoz przewoz1 = new Przewoz();
        przewoz1.setData(LocalDate.now());
        przewoz1.setGodzina(LocalTime.now());
        // Przypisz inne zależności (np. klient, autobus, trasa)

        // Zapisz do bazy
        przewozRepository.save(przewoz1);

        Przewoz przewoz2 = new Przewoz();
        przewoz2.setData(LocalDate.now().plusDays(1));
        przewoz2.setGodzina(LocalTime.of(10, 0));
        // Dodaj inne dane

        przewozRepository.save(przewoz2);
    }

    @Override
    public Przewoz getPrzewoz(int id) {
        return przewozRepository.findById((long) id)
                .orElseThrow(() -> new RuntimeException("Przewoz not found"));
    }

    @Override
    public List<Przewoz> getAll() {
        return przewozRepository.findAll();
    }

}
