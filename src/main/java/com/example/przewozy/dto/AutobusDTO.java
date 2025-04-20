package com.example.przewozy.dto;

import com.example.przewozy.entity.Autobus;
import com.example.przewozy.rest.AutobusController;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
@NoArgsConstructor
public class AutobusDTO extends RepresentationModel<AutobusDTO> {
    private Integer id;
    private String marka;
    private String model;
    private int liczbaMiejsc;
    private int rokProdukcji;

    public AutobusDTO(Autobus autobus) {
        super();
        this.id = autobus.getId();
        this.marka = autobus.getMarka();
        this.model = autobus.getModel();
        this.liczbaMiejsc = autobus.getLiczbaMiejsc();
        this.rokProdukcji = autobus.getRokProdukcji();
        this.add(linkTo(methodOn(AutobusController.class)
                .getPrzewozyForAutobus(autobus.getId())).withRel("przewozy"));
    }
}
