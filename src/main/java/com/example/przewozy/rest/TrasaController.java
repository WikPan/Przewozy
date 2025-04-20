package com.example.przewozy.rest;

import com.example.przewozy.dto.PrzewozDTO;
import com.example.przewozy.dto.TrasaDTO;
import com.example.przewozy.entity.Autobus;
import com.example.przewozy.entity.Trasa;
import com.example.przewozy.repo.TrasaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/trasy")
public class TrasaController {

    @Autowired
    private TrasaRepository trasaRepo;

    @GetMapping
    public CollectionModel<TrasaDTO> getTrasy(){
        List<TrasaDTO> trasyDTO = new ArrayList<>();
        for(Trasa trasa: trasaRepo.findAll())
            trasyDTO.add(new TrasaDTO(trasa));
        return CollectionModel.of(trasyDTO);
    }

    @GetMapping("/{id}")
    public TrasaDTO getTrasa(@PathVariable Integer id){
        Trasa t = trasaRepo.findById(id).orElse(null);
        return new TrasaDTO(t);
    }

    @PostMapping
    public ResponseEntity<?> createTrasa(@RequestBody Trasa przewoz ){
        przewoz = trasaRepo.save(przewoz);
        return ResponseEntity.ok("Dodano przewóz");
    }

    @GetMapping("/{id}/przewozy")
    public CollectionModel<PrzewozDTO> getPrzewozyForTrasa(@PathVariable Integer id) {
        Trasa trasa = trasaRepo.findById(id).orElse(null);
        if (trasa == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Autobus nie znaleziony");
        }

        List<PrzewozDTO> przewozyDTO = trasa.getPrzewozy().stream()
                .map(PrzewozDTO::new)
                .collect(Collectors.toList());

        return CollectionModel.of(przewozyDTO);
    }
}
