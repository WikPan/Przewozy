package com.example.przewozy.service;

import com.example.przewozy.dto.PrzewozDTO;
import com.example.przewozy.entity.Autobus;
import com.example.przewozy.entity.Przewoz;
import com.example.przewozy.entity.Trasa;
import com.example.przewozy.exception.ResourceNotFoundException;
import com.example.przewozy.repo.AutobusRepository;
import com.example.przewozy.repo.PrzewozRepository;
import com.example.przewozy.repo.TrasaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PrzewozServiceImpl implements PrzewozService {

    @Autowired
    private PrzewozRepository przewozRepo;

    @Autowired
    private TrasaRepository trasaRepo;

    @Autowired
    private AutobusRepository autobusRepo;

    @Override
    public CollectionModel<PrzewozDTO> getPrzewozyByParams(Integer trasaId, Integer autobusId) {
        List<Przewoz> przewozy;

        if (trasaId != null && autobusId != null) {
            przewozy = ((List<Przewoz>) przewozRepo.findAll()).stream()
                    .filter(p -> p.getTrasa() != null && p.getTrasa().getId().equals(trasaId))
                    .filter(p -> p.getAutobus() != null && p.getAutobus().getId().equals(autobusId))
                    .toList();
        } else if (trasaId != null) {
            przewozy = przewozRepo.findByTrasaId(trasaId);
        } else if (autobusId != null) {
            przewozy = przewozRepo.findByAutobusId(autobusId);
        } else {
            przewozy = (List<Przewoz>) przewozRepo.findAll();
        }

        List<PrzewozDTO> dtoList = przewozy.stream().map(PrzewozDTO::new).toList();
        return CollectionModel.of(dtoList);
    }

    @Override
    public CollectionModel<PrzewozDTO> getPrzewozy() {
        List<PrzewozDTO> przewozyDTO = new ArrayList<>();
        for (Przewoz przewoz : przewozRepo.findAll()) {
            przewozyDTO.add(new PrzewozDTO(przewoz));
        }
        return CollectionModel.of(przewozyDTO);
    }

    @Override
    public PrzewozDTO getPrzewoz(Integer id) {
        Przewoz p = przewozRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Przewóz nie znaleziony: " + id));
        return new PrzewozDTO(p);
    }

    @Override
    public PrzewozDTO createPrzewoz(PrzewozDTO dto) {
        Autobus autobus = autobusRepo.findById(dto.getAutobusId())
            .orElseThrow(() -> new ResourceNotFoundException("Autobus nie znaleziony: " + dto.getAutobusId()));
        Trasa trasa = trasaRepo.findById(dto.getTrasaId())
            .orElseThrow(() -> new ResourceNotFoundException("Trasa nie znaleziona: " + dto.getTrasaId()));

        Przewoz przewoz = new Przewoz();
        przewoz.setData(dto.getData());
        przewoz.setGodzina(dto.getGodzina());
        przewoz.setAutobus(autobus);
        przewoz.setTrasa(trasa);
        Przewoz saved = przewozRepo.save(przewoz);

        return new PrzewozDTO(saved);
    }

    @Override
    public Autobus getAutobusForPrzewoz(Integer id) {
        Przewoz przewoz = przewozRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Przewóz nie znaleziony: " + id));
        return przewoz.getAutobus();
    }

    @Override
    public Trasa getTrasaForPrzewoz(Integer id) {
        Przewoz przewoz = przewozRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Przewóz nie znaleziony: " + id));
        return przewoz.getTrasa();
    }

    @Override
    public ResponseEntity<?> updatePrzewoz(Integer id, Przewoz updatedPrzewoz) {
        return przewozRepo.findById(id)
            .map(przewoz -> {
                przewoz.setData(updatedPrzewoz.getData());
                przewoz.setGodzina(updatedPrzewoz.getGodzina());
                przewoz.setAutobus(updatedPrzewoz.getAutobus());
                przewoz.setTrasa(updatedPrzewoz.getTrasa());
                przewozRepo.save(przewoz);
                return ResponseEntity.ok("Zaktualizowano przewóz o id: " + id);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<?> deletePrzewoz(Integer id) {
        if (przewozRepo.existsById(id)) {
            przewozRepo.deleteById(id);
            return ResponseEntity.ok("Usunięto przewóz o id: " + id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
