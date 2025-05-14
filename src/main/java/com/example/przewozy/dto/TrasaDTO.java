package com.example.przewozy.dto;

import com.example.przewozy.entity.Trasa;
import com.example.przewozy.rest.TrasaController;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
@NoArgsConstructor
public class TrasaDTO extends RepresentationModel<TrasaDTO> {
    private Integer id;

    @NotBlank(message = "Punkt startowy nie może być pusty")
    private String punktStartowy;

    @NotBlank(message = "Punkt docelowy nie może być pusty")
    private String punktDocelowy;

    @Positive(message = "Dystans musi być większy od zera")
    private double dystansKm;

    public TrasaDTO(String punktStartowy, String punktDocelowy, double dystansKm) {
        this.punktStartowy = punktStartowy;
        this.punktDocelowy = punktDocelowy;
        this.dystansKm = dystansKm;
    }

    public TrasaDTO(Trasa trasa) {
        this.id = trasa.getId();
        this.punktStartowy = trasa.getPunktStartowy();
        this.punktDocelowy = trasa.getPunktDocelowy();
        this.dystansKm = trasa.getDystansKm();

        // Dodajemy linki HATEOAS
        this.add(linkTo(methodOn(TrasaController.class).getTrasa(trasa.getId())).withSelfRel());
        this.add(linkTo(methodOn(TrasaController.class).getPrzewozyForTrasa(trasa.getId())).withRel("przewozy"));
    }
}
