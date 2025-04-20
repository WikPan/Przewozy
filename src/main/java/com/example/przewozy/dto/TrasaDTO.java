package com.example.przewozy.dto;

import com.example.przewozy.entity.Trasa;
import com.example.przewozy.rest.TrasaController;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
@NoArgsConstructor
public class TrasaDTO extends RepresentationModel<TrasaDTO> {
    private Integer id;
    private String punktStartowy;
    private String punktDocelowy;
    private double dystansKm;

    public TrasaDTO(Trasa trasa){
        super();
        this.id = trasa.getId();
        this.punktStartowy = trasa.getPunktStartowy();
        this.punktDocelowy = trasa.getPunktDocelowy();
        this.dystansKm = trasa.getDystansKm();

        this.add(linkTo(methodOn(TrasaController.class)
                .getPrzewozyForTrasa(trasa.getId())).withRel("przewozy"));
    }
}
