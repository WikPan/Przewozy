package com.example.przewozy.service;

import com.example.przewozy.dto.BiletDTO;
import com.example.przewozy.dto.KlientDTO;
import com.example.przewozy.dto.PrzewozDTO;
import com.example.przewozy.entity.Bilet;
import com.example.przewozy.entity.Klient;
import com.example.przewozy.entity.Przewoz;
import com.example.przewozy.repo.BiletRepository;
import com.example.przewozy.repo.KlientRepository;
import com.example.przewozy.repo.PrzewozRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BiletServiceImpl implements BiletService {

    private final BiletRepository biletRepo;
    private final KlientRepository klientRepo;
    private final PrzewozRepository przewozRepo;

    @Override
    public List<BiletDTO> getAll() {
        return biletRepo.findAll().stream()
            .map(BiletDTO::new)
            .collect(Collectors.toList());
    }

    @Override
    public BiletDTO getById(Long id) {
        return biletRepo.findById(id)
            .map(BiletDTO::new)
            .orElseThrow(() -> new RuntimeException("Bilet nie znaleziony: " + id));
    }

    @Override
    public BiletDTO create(BiletDTO dto) {
        Klient klient = klientRepo.findById(dto.getKlientId())
            .orElseThrow(() -> new RuntimeException("Klient nie istnieje: " + dto.getKlientId()));
        Przewoz przewoz = przewozRepo.findById(dto.getPrzewozId())
            .orElseThrow(() -> new RuntimeException("Przewóz nie istnieje: " + dto.getPrzewozId()));

        Bilet bilet = new Bilet();
        bilet.setKlient(klient);
        bilet.setPrzewoz(przewoz);
        bilet.setMiejsce(dto.getMiejsce());
        bilet.setCena(dto.getCena());
        bilet.setStatus(dto.getStatus());

        return new BiletDTO(biletRepo.save(bilet));
    }

    @Override
    public BiletDTO update(Long id, BiletDTO dto) {
        Bilet bilet = biletRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Bilet nie znaleziony: " + id));

        if (!bilet.getKlient().getId().equals(dto.getKlientId())) {
            Klient k = klientRepo.findById(dto.getKlientId())
                .orElseThrow(() -> new RuntimeException("Klient nie istnieje: " + dto.getKlientId()));
            bilet.setKlient(k);
        }

        if (!bilet.getPrzewoz().getId().equals(dto.getPrzewozId())) {
            Przewoz p = przewozRepo.findById(dto.getPrzewozId())
                .orElseThrow(() -> new RuntimeException("Przewóz nie istnieje: " + dto.getPrzewozId()));
            bilet.setPrzewoz(p);
        }

        bilet.setMiejsce(dto.getMiejsce());
        bilet.setCena(dto.getCena());
        bilet.setStatus(dto.getStatus());

        return new BiletDTO(biletRepo.save(bilet));
    }

    @Override
    public void delete(Long id) {
        biletRepo.deleteById(id);
    }

    @Override
    public KlientDTO getKlientForBilet(Long id) {
        Bilet bilet = biletRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Bilet nie znaleziony: " + id));
        return new KlientDTO(bilet.getKlient());
    }

    @Override
    public PrzewozDTO getPrzewozForBilet(Long id) {
        Bilet bilet = biletRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Bilet nie znaleziony: " + id));
        return new PrzewozDTO(bilet.getPrzewoz());
    }
}
