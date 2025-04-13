package com.example.przewozy.rest;

import com.example.przewozy.dto.PrzewozDTO;
import com.example.przewozy.entity.Przewoz;
import com.example.przewozy.service.PrzewozService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApplicationRestController {

    private PrzewozService przewozService;

    public ApplicationRestController(PrzewozService przewozService){
        this.przewozService = przewozService;
    }

    @GetMapping("/przewozy")
    public List<Przewoz> findAll(){
        return przewozService.findAll();
    }

    @GetMapping("/przewozy/{index}")
    public Przewoz findPrzewoz(@PathVariable int index){
        return przewozService.findPrzewoz(index);
    }

    @PostMapping("/przewozy")
    public ResponseEntity<?> createPrzewoz(@RequestBody PrzewozDTO przewozDTO ){
        przewozService.createPrzewoz(przewozDTO);
        return ResponseEntity.ok("Dodano przewóz");
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
