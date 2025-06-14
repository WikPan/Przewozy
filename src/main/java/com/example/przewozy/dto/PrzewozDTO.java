package com.example.przewozy.dto;

import com.example.przewozy.entity.Autobus;
import com.example.przewozy.entity.Przewoz;
import com.example.przewozy.entity.Trasa;
import com.example.przewozy.rest.PrzewozController;
import com.example.przewozy.validation.ExistsInDatabase;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Relation(collectionRelation = "przewozDTOList")
@Data
@NoArgsConstructor
public class PrzewozDTO extends RepresentationModel<PrzewozDTO> {
    private Integer id;

    @NotNull
    private LocalDate data;

    @NotNull
    private LocalTime godzina;

    @NotNull
    @ExistsInDatabase(entity = Autobus.class)
    private Integer autobusId;

    @NotNull
    @ExistsInDatabase(entity = Trasa.class)
    private Integer trasaId;

    public PrzewozDTO(LocalDate data, LocalTime godzina, Integer autobusId, Integer trasaId) {
        this.data = data;
        this.godzina = godzina;
        this.autobusId = autobusId;
        this.trasaId = trasaId;
    }

    public PrzewozDTO(Przewoz przewoz) {
        this.id = przewoz.getId();
        this.data = przewoz.getData();
        this.godzina = przewoz.getGodzina();
        this.autobusId = przewoz.getAutobus() != null ? przewoz.getAutobus().getId() : null;
        this.trasaId = przewoz.getTrasa() != null ? przewoz.getTrasa().getId() : null;

        this.add(linkTo(methodOn(PrzewozController.class).getAutobusForPrzewoz(przewoz.getId())).withRel("autobus"));
        this.add(linkTo(methodOn(PrzewozController.class).getTrasaForPrzewoz(przewoz.getId())).withRel("trasa"));
        this.add(linkTo(methodOn(PrzewozController.class).getPrzewoz(przewoz.getId())).withSelfRel());
    }
}
