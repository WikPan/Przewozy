package com.example.przewozy.rest;

import com.example.przewozy.dto.PrzewozDTO;
import com.example.przewozy.dto.TrasaDTO;
import com.example.przewozy.entity.Trasa;
import com.example.przewozy.service.TrasaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trasy")
public class TrasaController {

    @Autowired
    private TrasaService trasaService;

    @GetMapping
    public CollectionModel<TrasaDTO> getTrasy() {
        return trasaService.findAll();
    }

    @GetMapping("/{id}")
    public TrasaDTO getTrasa(@PathVariable Integer id) {
        return trasaService.findById(id);
    }

    @PostMapping
    public ResponseEntity<String> createTrasa(@RequestBody Trasa trasa) {
        return ResponseEntity.ok(trasaService.create(trasa));
    }

    @GetMapping("/{id}/przewozy")
    public CollectionModel<PrzewozDTO> getPrzewozyForTrasa(@PathVariable Integer id) {
        return trasaService.getPrzewozyForTrasa(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTrasa(@PathVariable Integer id, @RequestBody Trasa trasa) {
        return ResponseEntity.ok(trasaService.update(id, trasa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTrasa(@PathVariable Integer id) {
        return ResponseEntity.ok(trasaService.delete(id));
    }
}
