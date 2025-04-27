package com.example.przewozy.rest;

import com.example.przewozy.dto.BiletDTO;
import com.example.przewozy.service.BiletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
    public ResponseEntity<BiletDTO> one(@PathVariable Long id) {
        return ResponseEntity.ok(biletService.getById(id));
    }

    @PostMapping
    public ResponseEntity<BiletDTO> create(@RequestBody BiletDTO dto) {
        BiletDTO created = biletService.create(dto);
        URI loc = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(created.getId())
            .toUri();
        return ResponseEntity.created(loc).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BiletDTO> update(
            @PathVariable Long id,
            @RequestBody BiletDTO dto) {
        return ResponseEntity.ok(biletService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        biletService.delete(id);
        return ResponseEntity.noContent().build();
    }
}