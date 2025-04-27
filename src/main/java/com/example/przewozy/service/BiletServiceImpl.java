package com.example.przewozy.service;

import com.example.przewozy.dto.BiletDTO;
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
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public BiletDTO getById(Long id) {
        return biletRepo.findById(id)
            .map(this::toDto)
            .orElseThrow(() -> new RuntimeException("Bilet nie znaleziony: " + id));
    }

    @Override
    public BiletDTO create(BiletDTO dto) {
        Klient k = klientRepo.findById(dto.getKlientId())
            .orElseThrow(() -> new RuntimeException("Klient nie istnieje: " + dto.getKlientId()));
        Przewoz p = przewozRepo.findById(dto.getPrzewozId())
            .orElseThrow(() -> new RuntimeException("Przewóz nie istnieje: " + dto.getPrzewozId()));

        Bilet b = new Bilet();
        b.setKlient(k);
        b.setPrzewoz(p);
        b.setMiejsce(dto.getMiejsce());
        b.setCena(dto.getCena());
        b.setStatus(dto.getStatus());

        Bilet saved = biletRepo.save(b);
        return toDto(saved);
    }

    @Override
    public BiletDTO update(Long id, BiletDTO dto) {
        Bilet b = biletRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Bilet nie znaleziony: " + id));

        if (!b.getKlient().getId().equals(dto.getKlientId())) {
            Klient k = klientRepo.findById(dto.getKlientId())
                .orElseThrow(() -> new RuntimeException("Klient nie istnieje: " + dto.getKlientId()));
            b.setKlient(k);
        }
        if (!b.getPrzewoz().getId().equals(dto.getPrzewozId())) {
            Przewoz p = przewozRepo.findById(dto.getPrzewozId())
                .orElseThrow(() -> new RuntimeException("Przewóz nie istnieje: " + dto.getPrzewozId()));
            b.setPrzewoz(p);
        }

        b.setMiejsce(dto.getMiejsce());
        b.setCena(dto.getCena());
        b.setStatus(dto.getStatus());

        Bilet updated = biletRepo.save(b);
        return toDto(updated);
    }

    @Override
    public void delete(Long id) {
        biletRepo.deleteById(id);
    }

    private BiletDTO toDto(Bilet b) {
        return new BiletDTO(
            b.getId(),
            b.getKlient().getId(),
            b.getPrzewoz().getId(),
            b.getMiejsce(),
            b.getCena(),
            b.getStatus()
        );
    }
}
