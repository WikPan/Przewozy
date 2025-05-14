package com.example.przewozy.assembler;

import com.example.przewozy.dto.TrasaDTO;
import com.example.przewozy.entity.Trasa;
import com.example.przewozy.rest.TrasaController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class TrasaModelAssembler
        implements RepresentationModelAssembler<Trasa, TrasaDTO> {

    @Override
    public TrasaDTO toModel(Trasa trasa) {
        return new TrasaDTO(trasa);
    }

    @Override
    public CollectionModel<TrasaDTO> toCollectionModel(Iterable<? extends Trasa> entities) {
        List<TrasaDTO> list = StreamSupport.stream(entities.spliterator(), false)
            .map(this::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(
            list,
            linkTo(methodOn(TrasaController.class).getTrasy()).withSelfRel()
        );
    }
}
