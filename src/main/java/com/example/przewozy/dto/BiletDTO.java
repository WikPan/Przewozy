package com.example.przewozy.dto;

import com.example.przewozy.entity.Bilet;
import com.example.przewozy.enums.StatusBiletu;
import com.example.przewozy.rest.BiletController;
import com.example.przewozy.validation.ExistsInDatabase;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
@NoArgsConstructor
public class BiletDTO extends RepresentationModel<BiletDTO> {

    private Integer id;

    @NotNull(message = "klientId nie może być nullem")
    @ExistsInDatabase(entity = com.example.przewozy.entity.Klient.class, message = "Klient nie istnieje")
    private Integer klientId;

    @NotNull(message = "przewozId nie może być nullem")
    @ExistsInDatabase(entity = com.example.przewozy.entity.Przewoz.class, message = "Przewóz nie istnieje")
    private Integer przewozId;

    @NotNull(message = "miejsce nie może być puste")
    private String miejsce;

    @NotNull(message = "cena nie może być pusta")
    private Integer cena;

    @NotNull(message = "status nie może być pusty")
    private StatusBiletu status;

    public BiletDTO(Integer klientId, Integer przewozId, String miejsce, Integer cena, StatusBiletu status) {
        this.klientId = klientId;
        this.przewozId = przewozId;
        this.miejsce = miejsce;
        this.cena = cena;
        this.status = status;
    }

    public BiletDTO(Bilet bilet) {
        this.id = bilet.getId();
        this.klientId = bilet.getKlient().getId();
        this.przewozId = bilet.getPrzewoz().getId();
        this.miejsce = bilet.getMiejsce();
        this.cena = bilet.getCena();
        this.status = bilet.getStatus();

        this.add(linkTo(methodOn(BiletController.class).getBilet(bilet.getId())).withSelfRel());
        this.add(linkTo(methodOn(BiletController.class).getKlientForBilet(bilet.getId())).withRel("klient"));
        this.add(linkTo(methodOn(BiletController.class).getPrzewozForBilet(bilet.getId())).withRel("przewoz"));
    }
}
