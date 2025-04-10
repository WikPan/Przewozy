package com.example.przewozy.rest;

import com.example.przewozy.entity.Przewoz;
import com.example.przewozy.service.PrzewozService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApplicationRestController {

    private PrzewozService przewozService;

    public ApplicationRestController(PrzewozService przewozService){
        this.przewozService = przewozService;
    }

    @GetMapping("/przewozy")
    public List<Przewoz> getAll(){
        return przewozService.getAll();
    }

    @GetMapping("/przewozy/{index}")
    public Przewoz getAll(@RequestParam int index){
        return przewozService.getPrzewoz(index);
    }
}
