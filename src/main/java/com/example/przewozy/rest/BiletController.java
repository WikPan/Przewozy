package com.example.przewozy.rest;

import com.example.przewozy.assembler.BiletModelAssembler;
import com.example.przewozy.dto.BiletDTO;
import com.example.przewozy.entity.Bilet;
import com.example.przewozy.enums.StatusBiletu;
import com.example.przewozy.service.BiletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/bilety")
@RequiredArgsConstructor
@Tag(name = "Bilety", description = "Operacje na biletach")
public class BiletController {

    private final BiletService biletService;
    private final BiletModelAssembler assembler;

    @Operation(summary = "Lista wszystkich biletów")
    @GetMapping
    public CollectionModel<BiletDTO> getAllBilety() {
        List<Bilet> all = biletService.getAllEncje();
        return assembler.toCollectionModel(all);
    }

    @Operation(summary = "Pobierz bilet po ID")
    @GetMapping("/{id}")
    public BiletDTO getBilet(@PathVariable Long id) {
        return biletService.getById(id);
    }

    @Operation(summary = "Utwórz nowy bilet")
    @PostMapping
    public ResponseEntity<BiletDTO> createBilet(@Valid @RequestBody BiletDTO dto) {
        BiletDTO created = biletService.create(dto);
        return ResponseEntity
                .created(URI.create("/bilety/" + created.getId()))
                .body(created);
    }

    @Operation(summary = "Zaktualizuj bilet")
    @PutMapping("/{id}")
    public BiletDTO updateBilet(
            @PathVariable Long id,
            @Valid @RequestBody BiletDTO dto) {
        return biletService.update(id, dto);
    }

    @Operation(summary = "Zmień status biletu.")
    @PatchMapping("/{id}")
    public BiletDTO patchBilet(
            @PathVariable Long id,
            @Valid @RequestBody StatusBiletu status){
            return biletService.changeBiletStatus(id, status);
    }

    @Operation(summary = "Usuń bilet")
    @DeleteMapping("/{id}")
    @ResponseStatus(code = org.springframework.http.HttpStatus.NO_CONTENT)
    public void deleteBilet(@PathVariable Long id) {
        biletService.delete(id);
    }

    @Operation(summary = "Pobierz klienta przypisanego do biletu")
    @GetMapping("/{id}/klient")
    public Object getKlientForBilet(@PathVariable Long id) {
        return biletService.getKlientForBilet(id);
    }

    @Operation(summary = "Pobierz przewóz przypisany do biletu")
    @GetMapping("/{id}/przewoz")
    public Object getPrzewozForBilet(@PathVariable Long id) {
        return biletService.getPrzewozForBilet(id);
    }
}

