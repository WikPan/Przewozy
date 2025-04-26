package com.example.przewozy.rest;

import com.example.przewozy.dto.CreatePrzewozDTO;
import com.example.przewozy.dto.PrzewozDTO;
import com.example.przewozy.entity.Autobus;
import com.example.przewozy.entity.Przewoz;
import com.example.przewozy.entity.Trasa;
import com.example.przewozy.repo.AutobusRepository;
import com.example.przewozy.repo.PrzewozRepository;
import com.example.przewozy.repo.TrasaRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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

    @Autowired
    private TrasaRepository trasaRepo;

    @Autowired
    private AutobusRepository autobusRepo;

    @PostConstruct
    private void generateData(){
        try {
            Przewoz przewoz1 = new Przewoz();
            przewoz1.setData(LocalDate.now());
            przewoz1.setGodzina(LocalTime.now());
            przewozRepo.save(przewoz1);

            Przewoz przewoz2 = new Przewoz();
            przewoz2.setData(LocalDate.now().plusDays(1));
            przewoz2.setGodzina(LocalTime.of(10, 0));
            przewozRepo.save(przewoz2);
        }catch (Exception e) {
            e.printStackTrace();
        }


        Trasa trasa = new Trasa();
        trasa.setDystansKm(15);
        trasa.setPunktDocelowy("kebab");
        trasa.setPunktStartowy("babek");

        trasaRepo.save(trasa);

        Autobus auto = new Autobus();
        auto.setMarka("hu");
        auto.setModel("mod");
        auto.setLiczbaMiejsc(15);
        auto.setRokProdukcji(1243);

        autobusRepo.save(auto);
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
    public ResponseEntity<?> createPrzewoz(@Valid @RequestBody CreatePrzewozDTO dto) {
        Autobus autobus = autobusRepo.findById(dto.getAutobusId())
                .orElseThrow(() -> new EntityNotFoundException("Autobus o podanym ID nie istnieje"));
        Trasa trasa = trasaRepo.findById(dto.getTrasaId())
                .orElseThrow(() -> new EntityNotFoundException("Trasa o podanym ID nie istnieje"));

        Przewoz przewoz = new Przewoz();
        przewoz.setData(dto.getData());
        przewoz.setGodzina(dto.getGodzina());
        przewoz.setAutobus(autobus);
        przewoz.setTrasa(trasa);

        przewozRepo.save(przewoz);
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

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePrzewoz(@PathVariable Integer id, @RequestBody Przewoz updatedPrzewoz) {
        return przewozRepo.findById(id)
                .map(przewoz -> {
                    przewoz.setData(updatedPrzewoz.getData());
                    przewoz.setGodzina(updatedPrzewoz.getGodzina());
                    przewoz.setAutobus(updatedPrzewoz.getAutobus());
                    przewoz.setTrasa(updatedPrzewoz.getTrasa());
                    przewozRepo.save(przewoz);
                    return ResponseEntity.ok("Zaktualizowano przewóz o id: " + id);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrzewoz(@PathVariable Integer id) {
        if (przewozRepo.existsById(id)) {
            przewozRepo.deleteById(id);
            return ResponseEntity.ok("Usunięto przewóz o id: " + id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
