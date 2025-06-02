package com.example.przewozy.service;

import com.example.przewozy.entity.Autobus;
import com.example.przewozy.entity.Przewoz;
import com.example.przewozy.exception.ResourceNotFoundException;
import com.example.przewozy.repo.AutobusRepository;
import com.example.przewozy.repo.PrzewozRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class AutobusServiceImpl implements AutobusService {

    private final AutobusRepository autobusRepository;
    private final PrzewozRepository przewozRepository;

    @Override
    public List<Autobus> getAllAutobusy() {
        return StreamSupport.stream(autobusRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public Autobus getAutobusById(Integer id) {
        return autobusRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Autobus nie znaleziony"));
    }

    @Override
    public Autobus createAutobus(Autobus autobus) {
        return autobusRepository.save(autobus);
    }

    @Override
    public Autobus updateAutobus(Integer id, Autobus updated) {
        return autobusRepository.findById(id)
            .map(a -> {
                a.setMarka(updated.getMarka());
                a.setModel(updated.getModel());
                a.setLiczbaMiejsc(updated.getLiczbaMiejsc());
                a.setRokProdukcji(updated.getRokProdukcji());
                return autobusRepository.save(a);
            })
            .orElseThrow(() -> new ResourceNotFoundException("Autobus nie znaleziony"));
    }

    @Override
    public void deleteAutobus(Integer id) {
        if (!autobusRepository.existsById(id)) {
            throw new ResourceNotFoundException("Autobus nie znaleziony");
        }
        autobusRepository.deleteById(id);
    }

    @Override
    public List<Przewoz> getPrzewozyForAutobus(Integer id) {
        getAutobusById(id); // wyrzuci 404, jeśli nie ma
        return przewozRepository.findByAutobusId(id);
    }
}
