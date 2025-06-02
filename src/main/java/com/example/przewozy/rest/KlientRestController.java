package com.example.przewozy.rest;

import com.example.przewozy.dto.BiletDTO;
import com.example.przewozy.dto.KlientDTO;
import com.example.przewozy.dto.PrzewozDTO;
import com.example.przewozy.entity.Bilet;
import com.example.przewozy.entity.Przewoz;
import com.example.przewozy.service.KlientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public ResponseEntity<KlientDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(klientService.findById(id));
    }

    @Operation(summary = "Utwórz nowego klienta")
    @PostMapping
    public ResponseEntity<KlientDTO> create(@RequestBody @Valid KlientDTO klientDTO) {
        KlientDTO created = klientService.create(klientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Zaktualizuj dane klienta")
    @PutMapping("/{id}")
    public ResponseEntity<KlientDTO> update(@PathVariable Integer id,
                                            @RequestBody @Valid KlientDTO klientDTO) {
        KlientDTO updated = klientService.update(id, klientDTO);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Usuń klienta")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Integer id) {
        Map<String, String> response = new HashMap<>();

        if (klientService.delete(id)) {
            response.put("message", "Klient został usunięty.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.put("message", "Klient posiada bilety, nie można usunąć.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(summary = "Pobierz wszystkie przewozy dla danego autobusu")
    @GetMapping("/{id}/bilety")
    public CollectionModel<BiletDTO> getBiletyForKlient(@PathVariable Integer id) {
        List<Bilet> przewozy = klientService.getBiletyForKlient(id);
        List<BiletDTO> dtos = przewozy.stream()
                .map(BiletDTO::new)
                .collect(Collectors.toList());

        return CollectionModel.of(dtos,
                linkTo(methodOn(KlientRestController.class).getBiletyForKlient(id)).withSelfRel(),
                linkTo(methodOn(KlientRestController.class).getById(id)).withRel("autobus")
        );
    }
}
