package com.example.przewozy.rest;

import com.example.przewozy.assembler.BiletModelAssembler;
import com.example.przewozy.dto.BiletDTO;
import com.example.przewozy.entity.Bilet;
import com.example.przewozy.service.BiletService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/bilety")
@RequiredArgsConstructor
public class BiletController {

    private final BiletService biletService;
    private final BiletModelAssembler assembler;

    @GetMapping
    public CollectionModel<BiletDTO> getAllBilety() {
        List<Bilet> all = biletService.getAllEncje();
        return assembler.toCollectionModel(all);
    }

    @GetMapping("/{id}")
    public BiletDTO getBilet(@PathVariable Long id) {
        return biletService.getById(id);
    }

    @PostMapping
    public ResponseEntity<BiletDTO> createBilet(@Valid @RequestBody BiletDTO dto) {
        BiletDTO created = biletService.create(dto);
        return ResponseEntity
            .created(URI.create("/bilety/" + created.getId()))
            .body(created);
    }

    @PutMapping("/{id}")
    public BiletDTO updateBilet(
            @PathVariable Long id,
            @Valid @RequestBody BiletDTO dto) {
        return biletService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = org.springframework.http.HttpStatus.NO_CONTENT)
    public void deleteBilet(@PathVariable Long id) {
        biletService.delete(id);
    }

    @GetMapping("/{id}/klient")
    public Object getKlientForBilet(@PathVariable Long id) {
        return biletService.getKlientForBilet(id);
    }

    @GetMapping("/{id}/przewoz")
    public Object getPrzewozForBilet(@PathVariable Long id) {
        return biletService.getPrzewozForBilet(id);
    }
}
