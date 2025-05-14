package com.example.przewozy.rest;

import com.example.przewozy.assembler.TrasaModelAssembler;
import com.example.przewozy.dto.PrzewozDTO;
import com.example.przewozy.dto.TrasaDTO;
import com.example.przewozy.entity.Przewoz;
import com.example.przewozy.entity.Trasa;
import com.example.przewozy.service.TrasaService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/trasy")
@RequiredArgsConstructor
@Validated
public class TrasaController {

    private final TrasaService trasaService;
    private final TrasaModelAssembler assembler;

    // 1) GET /trasy → HATEOAS CollectionModel
    @GetMapping
    public CollectionModel<TrasaDTO> getTrasy() {
        List<Trasa> all = trasaService.findAll();
        return assembler.toCollectionModel(all);
    }

    // 2) GET /trasy/{id} → HATEOAS EntityModel
    @GetMapping("/{id}")
    public EntityModel<TrasaDTO> getTrasa(@PathVariable Integer id) {
        Trasa t = trasaService.findById(id);
        return EntityModel.of(assembler.toModel(t));
    }

    // 3) POST /trasy → 200 OK + komunikat
    @PostMapping
    public ResponseEntity<String> createTrasa(@Valid @RequestBody Trasa trasa) {
        trasaService.create(trasa);
        return ResponseEntity
                .ok("Dodano trasę");
    }

    // 4) PUT /trasy/{id} → 200 OK + komunikat
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTrasa(
            @PathVariable Integer id,
            @Valid @RequestBody Trasa trasa
    ) {
        trasaService.update(id, trasa);
        return ResponseEntity
                .ok("Zaktualizowano trasę");
    }

    // 5) DELETE /trasy/{id} → 200 OK + komunikat
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTrasa(@PathVariable Integer id) {
        trasaService.delete(id);
        return ResponseEntity
                .ok("Usunięto trasę");
    }

    // 6) GET /trasy/{id}/przewozy → HATEOAS CollectionModel
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
