package com.example.przewozy.rest;

import com.example.przewozy.dto.PrzewozDTO;
import com.example.przewozy.entity.Autobus;
import com.example.przewozy.entity.Przewoz;
import com.example.przewozy.entity.Trasa;
import com.example.przewozy.service.PrzewozService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/przewozy")
@Tag(name = "Przewozy", description = "Operacje na przewozach")
public class PrzewozController {

    @Autowired
    private PrzewozService przewozService;

//    @PostConstruct
//    private void generateData() {
//        przewozService.generateData();
//    }

    @Operation(summary = "Pobierz listę przewozów, z opcjonalnym filtrowaniem po trasie lub autobusie")
    @GetMapping
    public CollectionModel<PrzewozDTO> getPrzewozy(
            @RequestParam(required = false) Integer trasaId,
            @RequestParam(required = false) Integer autobusId) {
        return przewozService.getPrzewozyByParams(trasaId, autobusId);
    }

    @Operation(summary = "Pobierz szczegóły przewozu po ID")
    @GetMapping("/{id}")
    public PrzewozDTO getPrzewoz(@PathVariable Integer id) {
        return przewozService.getPrzewoz(id);
    }

    @Operation(summary = "Utwórz nowy przewóz")
    @PostMapping
    public ResponseEntity<?> createPrzewoz(@Valid @RequestBody PrzewozDTO dto) {
        return przewozService.createPrzewoz(dto);
    }

    @Operation(summary = "Pobierz autobus przypisany do danego przewozu")
    @GetMapping("/{id}/autobus")
    public Autobus getAutobusForPrzewoz(@PathVariable Integer id) {
        return przewozService.getAutobusForPrzewoz(id);
    }

    @Operation(summary = "Pobierz trasę przypisaną do danego przewozu")
    @GetMapping("/{id}/trasa")
    public Trasa getTrasaForPrzewoz(@PathVariable Integer id) {
        return przewozService.getTrasaForPrzewoz(id);
    }

    @Operation(summary = "Zaktualizuj istniejący przewóz")
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePrzewoz(@PathVariable Integer id, @RequestBody Przewoz updatedPrzewoz) {
        return przewozService.updatePrzewoz(id, updatedPrzewoz);
    }

    @Operation(summary = "Usuń przewóz")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrzewoz(@PathVariable Integer id) {
        return przewozService.deletePrzewoz(id);
    }
}
