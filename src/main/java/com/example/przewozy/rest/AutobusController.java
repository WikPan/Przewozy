package com.example.przewozy.rest;

import com.example.przewozy.assembler.AutobusModelAssembler;
import com.example.przewozy.dto.AutobusDTO;
import com.example.przewozy.dto.PrzewozDTO;
import com.example.przewozy.entity.Autobus;
import com.example.przewozy.entity.Przewoz;
import com.example.przewozy.service.AutobusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/autobusy")
@RequiredArgsConstructor
@Tag(name = "Autobusy", description = "Operacje na autobusach")
@Validated
public class AutobusController {

    private final AutobusService autobusService;
    private final AutobusModelAssembler assembler;

    @Operation(summary = "Lista wszystkich autobusów (opcjonalnie filtrowana po marce)")
    @GetMapping
    public CollectionModel<AutobusDTO> getAllAutobusy(
        @RequestParam(value = "marka", required = false) String marka) {

        List<Autobus> wyniki = (marka == null)
            ? autobusService.getAllAutobusy()
            : autobusService.getAllAutobusy().stream()
                .filter(a -> a.getMarka().equalsIgnoreCase(marka))
                .collect(Collectors.toList());

        return assembler.toCollectionModel(wyniki);
    }

    @Operation(summary = "Pobierz pojedynczy autobus po ID")
    @GetMapping("/{id}")
    public EntityModel<AutobusDTO> getAutobusById(@PathVariable Integer id) {
        Autobus a = autobusService.getAutobusById(id);
        return EntityModel.of(assembler.toModel(a));
    }

    @Operation(summary = "Utwórz nowy autobus")
    @PostMapping
    public ResponseEntity<?> createAutobus(@Valid @RequestBody Autobus autobus) {
        Autobus saved = autobusService.createAutobus(autobus);
        AutobusDTO dto = assembler.toModel(saved);
        return ResponseEntity
            .created(dto.getRequiredLink("self").toUri())
            .body(dto);
    }

    @Operation(summary = "Zaktualizuj istniejący autobus")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAutobus(
        @PathVariable Integer id,
        @Valid @RequestBody Autobus updated) {

        Autobus a = autobusService.updateAutobus(id, updated);
        return ResponseEntity.ok(assembler.toModel(a));
    }

    @Operation(summary = "Usuń autobus")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAutobus(@PathVariable Integer id) {
        autobusService.deleteAutobus(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Pobierz wszystkie przewozy dla danego autobusu")
    @GetMapping("/{id}/przewozy")
    public CollectionModel<PrzewozDTO> getPrzewozyForAutobus(@PathVariable Integer id) {
        List<Przewoz> przewozy = autobusService.getPrzewozyForAutobus(id);
        List<PrzewozDTO> dtos = przewozy.stream()
            .map(PrzewozDTO::new)
            .collect(Collectors.toList());

        return CollectionModel.of(dtos,
            linkTo(methodOn(AutobusController.class).getPrzewozyForAutobus(id)).withSelfRel(),
            linkTo(methodOn(AutobusController.class).getAutobusById(id)).withRel("autobus")
        );
    }
}
