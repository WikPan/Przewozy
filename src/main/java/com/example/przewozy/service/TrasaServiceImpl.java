package com.example.przewozy.service;

import com.example.przewozy.dto.PrzewozDTO;
import com.example.przewozy.dto.TrasaDTO;
import com.example.przewozy.entity.Trasa;
import com.example.przewozy.repo.PrzewozRepository;
import com.example.przewozy.repo.TrasaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class TrasaServiceImpl implements TrasaService {

    private final TrasaRepository trasaRepo;
    private final PrzewozRepository przewozRepo;

    @Override
    public CollectionModel<TrasaDTO> findAll() {
        // zamiana Iterable<Trasa> na Stream<Trasa>
        List<TrasaDTO> trasyDTO = StreamSupport.stream(trasaRepo.findAll().spliterator(), false)
            .map(TrasaDTO::new)
            .collect(Collectors.toList());
        return CollectionModel.of(trasyDTO);
    }

    @Override
    public TrasaDTO findById(Integer id) {
        Trasa trasa = trasaRepo.findById(id)
            .orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Trasa nie znaleziona: " + id)
            );
        return new TrasaDTO(trasa);
    }

    @Override
    public String create(Trasa trasa) {
        trasaRepo.save(trasa);
        return "Dodano trasę";
    }

    @Override
    public CollectionModel<PrzewozDTO> getPrzewozyForTrasa(Integer trasaId) {
        List<PrzewozDTO> przDTO = przewozRepo.findByTrasaId(trasaId).stream()
            .map(PrzewozDTO::new)
            .collect(Collectors.toList());
        return CollectionModel.of(przDTO);
    }

    @Override
    public String update(Integer id, Trasa updated) {
        return trasaRepo.findById(id)
            .map(orig -> {
                orig.setPunktStartowy(updated.getPunktStartowy());
                orig.setPunktDocelowy(updated.getPunktDocelowy());
                orig.setDystansKm(updated.getDystansKm());
                trasaRepo.save(orig);
                return "Zaktualizowano trasę o id: " + id;
            })
            .orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Trasa nie znaleziona: " + id)
            );
    }

    @Override
    public String delete(Integer id) {
        if (!trasaRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trasa nie znaleziona: " + id);
        }
        trasaRepo.deleteById(id);
        return "Usunięto trasę o id: " + id;
    }
}
