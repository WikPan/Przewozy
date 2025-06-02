package com.example.przewozy.assembler;

import com.example.przewozy.dto.BiletDTO;
import com.example.przewozy.entity.Bilet;
import com.example.przewozy.rest.BiletController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class BiletModelAssembler
        implements RepresentationModelAssembler<Bilet, BiletDTO> {

    @Override
    public BiletDTO toModel(Bilet bilet) {
        return new BiletDTO(bilet);
    }

    @Override
    public CollectionModel<BiletDTO> toCollectionModel(Iterable<? extends Bilet> entities) {
        List<BiletDTO> list = StreamSupport.stream(entities.spliterator(), false)
            .map(this::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(
            list,
            linkTo(methodOn(BiletController.class).getAllBilety()).withSelfRel()
        );
    }
}
