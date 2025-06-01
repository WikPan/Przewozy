package com.example.przewozy.rest;

import com.example.przewozy.dto.KlientDTO;
import com.example.przewozy.service.KlientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/klienci")
@RequiredArgsConstructor
@Tag(name = "Klienci", description = "Operacje na klientach")
public class KlientRestController {

    private final KlientService klientService;

    @Operation(summary = "Lista wszystkich klientów")
    @GetMapping
    public ResponseEntity<List<KlientDTO>> getAll() {
        return ResponseEntity.ok(klientService.findAll());
    }

    @Operation(summary = "Pobierz klienta po ID")
    @GetMapping("/{id}")
    public ResponseEntity<KlientDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(klientService.findById(id));
    }

    @Operation(summary = "Utwórz nowego klienta")
    @PostMapping
    public ResponseEntity<KlientDTO> create(@RequestBody @Valid KlientDTO klientDTO) {
        KlientDTO created = klientService.create(klientDTO);
        return ResponseEntity.ok(created);
    }

    @Operation(summary = "Zaktualizuj dane klienta")
    @PutMapping("/{id}")
    public ResponseEntity<KlientDTO> update(@PathVariable Long id,
                                            @RequestBody @Valid KlientDTO klientDTO) {
        KlientDTO updated = klientService.update(id, klientDTO);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Usuń klienta")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        klientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
