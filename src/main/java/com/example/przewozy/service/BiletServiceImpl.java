package com.example.przewozy.service;

import com.example.przewozy.dto.BiletDTO;
import com.example.przewozy.dto.KlientDTO;
import com.example.przewozy.dto.PrzewozDTO;
import com.example.przewozy.entity.Bilet;
import com.example.przewozy.entity.Klient;
import com.example.przewozy.entity.Przewoz;
import com.example.przewozy.exception.ResourceNotFoundException;
import com.example.przewozy.repo.BiletRepository;
import com.example.przewozy.repo.KlientRepository;
import com.example.przewozy.repo.PrzewozRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BiletServiceImpl implements BiletService {

    private final BiletRepository biletRepo;
    private final KlientRepository klientRepo;
    private final PrzewozRepository przewozRepo;

    @Override
    public List<Bilet> getAllEncje() {
        return biletRepo.findAll();
    }

    @Override
    public BiletDTO getById(Long id) {
        Bilet b = biletRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Bilet nie znaleziony: " + id));
        return new BiletDTO(b);
    }

    @Override
    public BiletDTO create(BiletDTO dto) {
        Klient klient = klientRepo.findById(dto.getKlientId())
            .orElseThrow(() -> new ResourceNotFoundException("Klient nie istnieje: " + dto.getKlientId()));
        Przewoz przewoz = przewozRepo.findById(dto.getPrzewozId())
            .orElseThrow(() -> new ResourceNotFoundException("Przewóz nie istnieje: " + dto.getPrzewozId()));

        Bilet bilet = new Bilet();
        bilet.setKlient(klient);
        bilet.setPrzewoz(przewoz);
        bilet.setMiejsce(dto.getMiejsce());
        bilet.setCena(dto.getCena());
        bilet.setStatus(dto.getStatus());

        Bilet saved = biletRepo.save(bilet);
        return new BiletDTO(saved);
    }

    @Override
    public BiletDTO update(Long id, BiletDTO dto) {
        Bilet bilet = biletRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Bilet nie znaleziony: " + id));

        if (!bilet.getKlient().getId().equals(dto.getKlientId())) {
            Klient k = klientRepo.findById(dto.getKlientId())
                .orElseThrow(() -> new ResourceNotFoundException("Klient nie istnieje: " + dto.getKlientId()));
            bilet.setKlient(k);
        }

        if (!bilet.getPrzewoz().getId().equals(dto.getPrzewozId())) {
            Przewoz p = przewozRepo.findById(dto.getPrzewozId())
                .orElseThrow(() -> new ResourceNotFoundException("Przewóz nie istnieje: " + dto.getPrzewozId()));
            bilet.setPrzewoz(p);
        }

        bilet.setMiejsce(dto.getMiejsce());
        bilet.setCena(dto.getCena());
        bilet.setStatus(dto.getStatus());

        Bilet updated = biletRepo.save(bilet);
        return new BiletDTO(updated);
    }

    @Override
    public void delete(Long id) {
        if (!biletRepo.existsById(id)) {
            throw new ResourceNotFoundException("Bilet nie znaleziony: " + id);
        }
        biletRepo.deleteById(id);
    }

    @Override
    public KlientDTO getKlientForBilet(Long id) {
        Bilet b = biletRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Bilet nie znaleziony: " + id));
        return new KlientDTO(b.getKlient());
    }

    @Override
    public PrzewozDTO getPrzewozForBilet(Long id) {
        Bilet b = biletRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Bilet nie znaleziony: " + id));
        return new PrzewozDTO(b.getPrzewoz());
    }
}
