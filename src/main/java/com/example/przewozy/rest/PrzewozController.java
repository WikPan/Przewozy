package com.example.przewozy.rest;

import com.example.przewozy.dto.PrzewozDTO;
import com.example.przewozy.entity.*;
import com.example.przewozy.repo.PrzewozRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/przewozy")
public class PrzewozController {

    @Autowired
    private PrzewozRepository przewozRepo;

    @PostConstruct
    private void generateData(){
        Przewoz przewoz1 = new Przewoz();
        przewoz1.setData(LocalDate.now());
        przewoz1.setGodzina(LocalTime.now());
        przewozRepo.save(przewoz1);

        Przewoz przewoz2 = new Przewoz();
        przewoz2.setData(LocalDate.now().plusDays(1));
        przewoz2.setGodzina(LocalTime.of(10, 0));

        przewozRepo.save(przewoz2);
    }

    @GetMapping
    public CollectionModel<PrzewozDTO> getPrzewozy(){
        List<PrzewozDTO> przewozyDTO = new ArrayList<>();
        for(Przewoz przewoz: przewozRepo.findAll())
            przewozyDTO.add(new PrzewozDTO(przewoz));
        return CollectionModel.of(przewozyDTO);
    }

    @GetMapping("/{id}")
    public PrzewozDTO getPrzewoz(@PathVariable Integer id){
        Przewoz p = przewozRepo.findById(id).orElse(null);
        return new PrzewozDTO(p);
    }

    @PostMapping
    public ResponseEntity<?> createPrzewoz(@RequestBody Przewoz przewoz ){
        przewoz = przewozRepo.save(przewoz);
        return ResponseEntity.ok("Dodano przewóz");
    }

    @GetMapping("/{id}/autobus")
    public Autobus getAutobusForPrzewoz(@PathVariable Integer id) {
        Przewoz przewoz = przewozRepo.findById(id).orElse(null);
        return przewoz.getAutobus();
    }

    @GetMapping("/{id}/trasa")
    public Trasa getTrasaForPrzewoz(@PathVariable Integer id) {
        Przewoz przewoz = przewozRepo.findById(id).orElse(null);
        return przewoz.getTrasa();
    }

    //@PutMapping("/przewozy")
    //public void updatePrzewoz(@PathVariable int index){
    //    przewozService.updatePrzewoz(index);
    //}

    //@DeleteMapping("/przewozy/{index}")
    //public void removePrzewoz(@PathVariable int index){
    //    przewozService.deletePrzewoz(index);
    //}
}
