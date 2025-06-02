package com.example.przewozy.dto;

import com.example.przewozy.entity.Autobus;
import com.example.przewozy.rest.AutobusController;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Relation(collectionRelation = "autobusDTOList")
@Data @NoArgsConstructor
public class AutobusDTO extends RepresentationModel<AutobusDTO> {

    private Integer id;

    @NotBlank(message = "Marka nie może być pusta")
    private String marka;

    @NotBlank(message = "Model nie może być pusty")
    private String model;

    @Positive(message = "Liczba miejsc musi być większa od zera")
    private int liczbaMiejsc;

    @Min(value = 1950, message = "Rok produkcji musi być >= 1950")
    private int rokProdukcji;

    public AutobusDTO(Autobus autobus) {
        this.id = autobus.getId();
        this.marka = autobus.getMarka();
        this.model = autobus.getModel();
        this.liczbaMiejsc = autobus.getLiczbaMiejsc();
        this.rokProdukcji = autobus.getRokProdukcji();

        add(linkTo(methodOn(AutobusController.class).getAutobusById(id)).withSelfRel());
        add(linkTo(methodOn(AutobusController.class).getPrzewozyForAutobus(id)).withRel("przewozy"));
    }
}
