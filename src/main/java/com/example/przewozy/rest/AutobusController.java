package com.example.przewozy.rest;

import com.example.przewozy.dto.AutobusDTO;
import com.example.przewozy.dto.PrzewozDTO;
import com.example.przewozy.entity.Autobus;
import com.example.przewozy.repo.AutobusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autobusy")
public class AutobusController {

    @Autowired
    private AutobusRepository autoRepo;

    @GetMapping
    public CollectionModel<AutobusDTO> getAutobusy(){
        List<AutobusDTO> autobusyDTO = new ArrayList<>();
        for(Autobus przewoz: autoRepo.findAll())
            autobusyDTO.add(new AutobusDTO(przewoz));
        return CollectionModel.of(autobusyDTO);
    }

    @GetMapping("/{id}")
    public AutobusDTO getAutobus(@PathVariable Integer id){
        Autobus a = autoRepo.findById(id).orElse(null);
        return new AutobusDTO(a);
    }

    @PostMapping
    public ResponseEntity<?> createAutobus(@RequestBody Autobus autobus ){
        autobus = autoRepo.save(autobus);
        return ResponseEntity.ok("Dodano autobus");
    }

    @GetMapping("/{id}/przewozy")
    public CollectionModel<PrzewozDTO> getPrzewozyForAutobus(@PathVariable Integer id) {
        Autobus autobus = autoRepo.findById(id).orElse(null);
        if (autobus == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Autobus nie znaleziony");
        }

        List<PrzewozDTO> przewozyDTO = autobus.getPrzewozy().stream()
                .map(PrzewozDTO::new)
                .collect(Collectors.toList());

        return CollectionModel.of(przewozyDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAutobus(@PathVariable Integer id, @RequestBody Autobus updatedAutobus) {
        return autoRepo.findById(id).map(autobus -> {
            autobus.setMarka(updatedAutobus.getMarka());
            autobus.setModel(updatedAutobus.getModel());
            autobus.setLiczbaMiejsc(updatedAutobus.getLiczbaMiejsc());
            autobus.setRokProdukcji(updatedAutobus.getRokProdukcji());

            autoRepo.save(autobus);
            return ResponseEntity.ok("Zaktualizowano autobus o id: " + id);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAutobus(@PathVariable Integer id) {
        if (autoRepo.existsById(id)) {
            autoRepo.deleteById(id);
            return ResponseEntity.ok("Usunięto autobus o id: " + id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
