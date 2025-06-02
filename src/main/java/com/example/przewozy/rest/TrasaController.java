package com.example.przewozy.rest;

import com.example.przewozy.assembler.TrasaModelAssembler;
import com.example.przewozy.dto.PrzewozDTO;
import com.example.przewozy.dto.TrasaDTO;
import com.example.przewozy.entity.Przewoz;
import com.example.przewozy.entity.Trasa;
import com.example.przewozy.service.TrasaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/trasy")
@RequiredArgsConstructor
@Validated
@Tag(name = "Trasy", description = "Operacje na trasach przejazdów")
public class TrasaController {

    private final TrasaService trasaService;
    private final TrasaModelAssembler assembler;

    @Operation(summary = "Pobierz listę wszystkich tras")
    @GetMapping
    public CollectionModel<TrasaDTO> getTrasy() {
        List<Trasa> all = trasaService.findAll();
        return assembler.toCollectionModel(all);
    }

    @Operation(summary = "Pobierz trasę po ID")
    @GetMapping("/{id}")
    public EntityModel<TrasaDTO> getTrasa(@PathVariable Integer id) {
        Trasa t = trasaService.findById(id);
        return EntityModel.of(assembler.toModel(t));
    }

    @Operation(summary = "Utwórz nową trasę")
    @PostMapping
    public ResponseEntity<String> createTrasa(@Valid @RequestBody Trasa trasa) {
        trasaService.create(trasa);
        return ResponseEntity.ok("Dodano trasę");
    }

    @Operation(summary = "Zaktualizuj istniejącą trasę")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTrasa(
            @PathVariable Integer id,
            @Valid @RequestBody Trasa trasa
    ) {
        trasaService.update(id, trasa);
        return ResponseEntity.ok("Zaktualizowano trasę");
    }

    @Operation(summary = "Usuń trasę po ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTrasa(@PathVariable Integer id) {
        trasaService.delete(id);
        return ResponseEntity.ok("Usunięto trasę");
    }

    @Operation(summary = "Pobierz przewozy przypisane do trasy")
    @GetMapping("/{id}/przewozy")
    public CollectionModel<PrzewozDTO> getPrzewozyForTrasa(@PathVariable Integer id) {
        List<Przewoz> list = trasaService.getPrzewozyForTrasa(id);
        var dtos = list.stream().map(PrzewozDTO::new).collect(Collectors.toList());
        return CollectionModel.of(
                dtos,
                linkTo(methodOn(TrasaController.class).getPrzewozyForTrasa(id)).withSelfRel(),
                linkTo(methodOn(TrasaController.class).getTrasa(id)).withRel("trasa")
        );
    }
}
