package com.example.przewozy.rest;

import com.example.przewozy.dto.BiletDTO;
import com.example.przewozy.dto.KlientDTO;
import com.example.przewozy.dto.PrzewozDTO;
import com.example.przewozy.service.BiletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/bilety")
@RequiredArgsConstructor
public class BiletController {

    private final BiletService biletService;

    @GetMapping
    public ResponseEntity<List<BiletDTO>> all() {
        return ResponseEntity.ok(biletService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BiletDTO> getBilet(@PathVariable Long id) {
        return ResponseEntity.ok(biletService.getById(id));
    }

    @PostMapping
    public ResponseEntity<BiletDTO> create(@RequestBody @Valid BiletDTO dto) {
        BiletDTO created = biletService.create(dto);
        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(created.getId())
            .toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BiletDTO> update(@PathVariable Long id, @RequestBody @Valid BiletDTO dto) {
        return ResponseEntity.ok(biletService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        biletService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/klient")
    public KlientDTO getKlientForBilet(@PathVariable Long id) {
        return biletService.getKlientForBilet(id);
    }

    @GetMapping("/{id}/przewoz")
    public PrzewozDTO getPrzewozForBilet(@PathVariable Long id) {
        return biletService.getPrzewozForBilet(id);
    }
}

