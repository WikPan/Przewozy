package com.example.przewozy.service;

import com.example.przewozy.entity.Przewoz;
import com.example.przewozy.entity.Trasa;
import com.example.przewozy.exception.ResourceNotFoundException;
import com.example.przewozy.repo.PrzewozRepository;
import com.example.przewozy.repo.TrasaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class TrasaServiceImpl implements TrasaService {

    private final TrasaRepository trasaRepo;
    private final PrzewozRepository przewozRepo;

    @Override
    public List<Trasa> findAll() {
        return StreamSupport.stream(trasaRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Trasa findById(Integer id) {
        return trasaRepo.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Trasa nie znaleziona: " + id)
                );
    }

    @Override
    public Trasa create(Trasa trasa) {
        return trasaRepo.save(trasa);
    }

    @Override
    public Trasa update(Integer id, Trasa updated) {
        return trasaRepo.findById(id)
                .map(orig -> {
                    orig.setPunktStartowy(updated.getPunktStartowy());
                    orig.setPunktDocelowy(updated.getPunktDocelowy());
                    orig.setDystansKm(updated.getDystansKm());
                    return trasaRepo.save(orig);
                })
                .orElseThrow(() ->
                    new ResourceNotFoundException("Trasa nie znaleziona: " + id)
                );
    }

    @Override
    public void delete(Integer id) {
        if (!trasaRepo.existsById(id)) {
            throw new ResourceNotFoundException("Trasa nie znaleziona: " + id);
        }
        trasaRepo.deleteById(id);
    }

    @Override
    public List<Przewoz> getPrzewozyForTrasa(Integer id) {
        // Rzuć 404 jeśli nie ma trasy
        findById(id);
        // Pobierz przewozy powiązane z tą trasą
        return przewozRepo.findByTrasaId(id);
    }
}
