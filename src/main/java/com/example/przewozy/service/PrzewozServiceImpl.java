package com.example.przewozy.service;

import com.example.przewozy.dto.PrzewozDTO;
import com.example.przewozy.entity.Autobus;
import com.example.przewozy.entity.Przewoz;
import com.example.przewozy.entity.Trasa;
import com.example.przewozy.exception.ResourceNotFoundException;
import com.example.przewozy.repo.AutobusRepository;
import com.example.przewozy.repo.PrzewozRepository;
import com.example.przewozy.repo.TrasaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PrzewozServiceImpl implements PrzewozService {

    @Autowired
    private PrzewozRepository przewozRepo;

    @Autowired
    private TrasaRepository trasaRepo;

    @Autowired
    private AutobusRepository autobusRepo;

    @PostConstruct
    @Override
    public void generateData() {
        try {
            Przewoz przewoz1 = new Przewoz();
            przewoz1.setData(LocalDate.now());
            przewoz1.setGodzina(LocalTime.now());
            przewozRepo.save(przewoz1);

            Przewoz przewoz2 = new Przewoz();
            przewoz2.setData(LocalDate.now().plusDays(1));
            przewoz2.setGodzina(LocalTime.of(10, 0));
            przewozRepo.save(przewoz2);
        } catch (Exception e) {
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

    @Override
    public CollectionModel<PrzewozDTO> getPrzewozy() {
        List<PrzewozDTO> przewozyDTO = new ArrayList<>();
        for (Przewoz przewoz : przewozRepo.findAll()) {
            przewozyDTO.add(new PrzewozDTO(przewoz));
        }
        return CollectionModel.of(przewozyDTO);
    }

    @Override
    public PrzewozDTO getPrzewoz(Integer id) {
        Przewoz p = przewozRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Przewóz nie znaleziony: " + id));
        return new PrzewozDTO(p);
    }

    @Override
    public ResponseEntity<?> createPrzewoz(PrzewozDTO dto) {
        Autobus autobus = autobusRepo.findById(dto.getAutobusId())
            .orElseThrow(() -> new ResourceNotFoundException("Autobus nie znaleziony: " + dto.getAutobusId()));
        Trasa trasa = trasaRepo.findById(dto.getTrasaId())
            .orElseThrow(() -> new ResourceNotFoundException("Trasa nie znaleziona: " + dto.getTrasaId()));

        Przewoz przewoz = new Przewoz();
        przewoz.setData(dto.getData());
        przewoz.setGodzina(dto.getGodzina());
        przewoz.setAutobus(autobus);
        przewoz.setTrasa(trasa);
        przewozRepo.save(przewoz);

        return ResponseEntity.ok("Dodano przewóz");
    }

    @Override
    public Autobus getAutobusForPrzewoz(Integer id) {
        Przewoz przewoz = przewozRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Przewóz nie znaleziony: " + id));
        return przewoz.getAutobus();
    }

    @Override
    public Trasa getTrasaForPrzewoz(Integer id) {
        Przewoz przewoz = przewozRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Przewóz nie znaleziony: " + id));
        return przewoz.getTrasa();
    }

    @Override
    public ResponseEntity<?> updatePrzewoz(Integer id, Przewoz updatedPrzewoz) {
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

    @Override
    public ResponseEntity<?> deletePrzewoz(Integer id) {
        if (przewozRepo.existsById(id)) {
            przewozRepo.deleteById(id);
            return ResponseEntity.ok("Usunięto przewóz o id: " + id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
