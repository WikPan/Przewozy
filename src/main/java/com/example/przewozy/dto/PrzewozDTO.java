package com.example.przewozy.dto;

import com.example.przewozy.entity.Przewoz;
import com.example.przewozy.rest.PrzewozController;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
@NoArgsConstructor
public class PrzewozDTO extends RepresentationModel<PrzewozDTO> {
    private Integer id;
    private LocalDate data;
    private LocalTime godzina;

    public PrzewozDTO(Przewoz przewoz) {
        super();
        this.id = przewoz.getId();
        this.data = przewoz.getData();
        this.godzina = przewoz.getGodzina();
        this.add(linkTo(methodOn(PrzewozController.class)
                .getAutobusForPrzewoz(przewoz.getId())).withRel("autobus"));
        this.add(linkTo(methodOn(PrzewozController.class)
                .getTrasaForPrzewoz(przewoz.getId())).withRel("trasa"));
    }

}

