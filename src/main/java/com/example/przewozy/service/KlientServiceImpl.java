package com.example.przewozy.service;

import com.example.przewozy.dto.KlientDTO;
import com.example.przewozy.entity.Klient;
import com.example.przewozy.repo.KlientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KlientServiceImpl implements KlientService {

    @Autowired
    private KlientRepository klientRepo;

    @Override
    public List<Klient> findAll() {
        return klientRepo.findAll();
    }

    @Override
    public Klient findById(Long id) {
        return klientRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Klient nie znaleziony: " + id));
    }

    @Override
    public void create(KlientDTO dto) {
        Klient k = new Klient();
        k.setImie(dto.getImie());
        k.setNazwisko(dto.getNazwisko());
        k.setTelefon(dto.getTelefon());
        k.setEmail(dto.getEmail());
        klientRepo.save(k);
    }

    @Override
    public void update(Long id, KlientDTO dto) {
        Klient k = findById(id);
        k.setImie(dto.getImie());
        k.setNazwisko(dto.getNazwisko());
        k.setTelefon(dto.getTelefon());
        k.setEmail(dto.getEmail());
        klientRepo.save(k);
    }

    @Override
    public void delete(Long id) {
        klientRepo.deleteById(id);
    }

    @Override
    public Page<Klient> findAllPaged(Pageable pageable) {
        return klientRepo.findAll(pageable);
    }
}
