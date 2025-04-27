package com.example.przewozy.rest;

import com.example.przewozy.dto.KlientDTO;
import com.example.przewozy.entity.Klient;
import com.example.przewozy.service.KlientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/klienci")
public class KlientRestController {

    @Autowired
    private KlientService klientService;

    @GetMapping
    public List<Klient> getAll() {
        return klientService.findAll();
    }

    @GetMapping("/{id}")
    public Klient getById(@PathVariable Long id) {
        return klientService.findById(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody KlientDTO dto) {
        klientService.create(dto);
        return ResponseEntity.ok("Dodano klienta");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody KlientDTO dto) {
        klientService.update(id, dto);
        return ResponseEntity.ok("Zaktualizowano klienta");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        klientService.delete(id);
        return ResponseEntity.ok("Usunięto klienta");
    }

    @GetMapping("/page")
    public Page<Klient> getPaged(Pageable pageable) {
        return klientService.findAllPaged(pageable);
    }
}
