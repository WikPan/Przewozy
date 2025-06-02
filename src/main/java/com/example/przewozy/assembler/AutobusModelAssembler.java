package com.example.przewozy.assembler;

import com.example.przewozy.dto.AutobusDTO;
import com.example.przewozy.entity.Autobus;
import com.example.przewozy.rest.AutobusController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class AutobusModelAssembler
    implements RepresentationModelAssembler<Autobus, AutobusDTO> {

    @Override
    public AutobusDTO toModel(Autobus autobus) {
        AutobusDTO dto = new AutobusDTO(autobus);
        // Konstruktor DTO już dodaje linki self i przewozy
        return dto;
    }

    @Override
    public CollectionModel<AutobusDTO> toCollectionModel(Iterable<? extends Autobus> entities) {
        List<AutobusDTO> list = StreamSupport.stream(entities.spliterator(), false)
            .map(this::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(list,
            linkTo(methodOn(AutobusController.class).getAllAutobusy(null)).withSelfRel()
        );
    }
}
