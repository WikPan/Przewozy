package com.example.przewozy.rest;

import com.example.przewozy.dto.KlientDTO;
import com.example.przewozy.service.KlientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/klienci")
@RequiredArgsConstructor
public class KlientRestController {

    private final KlientService klientService;

    @GetMapping
    public ResponseEntity<List<KlientDTO>> getAll() {
        return ResponseEntity.ok(klientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KlientDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(klientService.findById(id));
    }

    @PostMapping
    public ResponseEntity<KlientDTO> create(@RequestBody @Valid KlientDTO klientDTO) {
        KlientDTO created = klientService.create(klientDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KlientDTO> update(@PathVariable Long id,
                                            @RequestBody @Valid KlientDTO klientDTO) {
        KlientDTO updated = klientService.update(id, klientDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        klientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
