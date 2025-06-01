package com.example.przewozy.service;

import com.example.przewozy.dto.KlientDTO;
import com.example.przewozy.entity.Klient;
import com.example.przewozy.repo.KlientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KlientServiceImpl implements KlientService {

    private final KlientRepository klientRepo;

    @Override
    public List<KlientDTO> findAll() {
        return klientRepo.findAll().stream()
                .map(KlientDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public KlientDTO findById(Long id) {
        Klient k = klientRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Klient nie znaleziony: " + id));
        return new KlientDTO(k);
    }

    @Override
    public KlientDTO create(KlientDTO dto) {
        Klient k = new Klient();
        k.setImie(dto.getImie());
        k.setNazwisko(dto.getNazwisko());
        k.setTelefon(dto.getTelefon());
        k.setEmail(dto.getEmail());
        Klient saved = klientRepo.save(k);
        return new KlientDTO(saved);
    }

    @Override
    public KlientDTO update(Long id, KlientDTO dto) {
        Klient k = klientRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Klient nie znaleziony: " + id));
        k.setImie(dto.getImie());
        k.setNazwisko(dto.getNazwisko());
        k.setTelefon(dto.getTelefon());
        k.setEmail(dto.getEmail());
        Klient updated = klientRepo.save(k);
        return new KlientDTO(updated);
    }

    @Override
    public boolean delete(Long id) {
        Klient k = klientRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Klient nie znaleziony: " + id));
        if(k.getBilety()!=null) return false;
        else {
            klientRepo.deleteById(id);
            return true;
        }
    }
}
