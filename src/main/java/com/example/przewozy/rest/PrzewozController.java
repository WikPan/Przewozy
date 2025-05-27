package com.example.przewozy.rest;

import com.example.przewozy.dto.PrzewozDTO;
import com.example.przewozy.entity.Autobus;
import com.example.przewozy.entity.Przewoz;
import com.example.przewozy.entity.Trasa;
import com.example.przewozy.service.PrzewozService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/przewozy")
public class PrzewozController {

    @Autowired
    private PrzewozService przewozService;

    @PostConstruct
    private void generateData() {
        przewozService.generateData();
    }

    @GetMapping
    public CollectionModel<PrzewozDTO> getPrzewozy(
            @RequestParam(required = false) Integer trasaId,
            @RequestParam(required = false) Integer autobusId) {
        return przewozService.getPrzewozyByParams(trasaId, autobusId);
    }

    @GetMapping("/{id}")
    public PrzewozDTO getPrzewoz(@PathVariable Integer id) {
        return przewozService.getPrzewoz(id);
    }

    @PostMapping
    public ResponseEntity<?> createPrzewoz(@Valid @RequestBody PrzewozDTO dto) {
        return przewozService.createPrzewoz(dto);
    }

    @GetMapping("/{id}/autobus")
    public Autobus getAutobusForPrzewoz(@PathVariable Integer id) {
        return przewozService.getAutobusForPrzewoz(id);
    }

    @GetMapping("/{id}/trasa")
    public Trasa getTrasaForPrzewoz(@PathVariable Integer id) {
        return przewozService.getTrasaForPrzewoz(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePrzewoz(@PathVariable Integer id, @RequestBody Przewoz updatedPrzewoz) {
        return przewozService.updatePrzewoz(id, updatedPrzewoz);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrzewoz(@PathVariable Integer id) {
        return przewozService.deletePrzewoz(id);
    }
}
