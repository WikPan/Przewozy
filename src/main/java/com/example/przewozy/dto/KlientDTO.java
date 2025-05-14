package com.example.przewozy.dto;

import com.example.przewozy.entity.Klient;
import com.example.przewozy.rest.KlientRestController;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KlientDTO extends RepresentationModel<KlientDTO> {
    private Long id;

    @NotBlank(message = "Imię nie może być puste")
    @Size(max = 50, message = "Imię może mieć maksymalnie 50 znaków")
    private String imie;

    @NotBlank(message = "Nazwisko nie może być puste")
    @Size(max = 50, message = "Nazwisko może mieć maksymalnie 50 znaków")
    private String nazwisko;

    @NotBlank(message = "Telefon nie może być pusty")
    @Pattern(regexp = "\\d{9}", message = "Telefon musi składać się z 9 cyfr")
    private String telefon;

    @NotBlank(message = "Email nie może być pusty")
    @Email(message = "Niepoprawny adres email")
    private String email;

    public KlientDTO(String imie, String nazwisko, String telefon, String email) {
        this(null, imie, nazwisko, telefon, email);
    }

    public KlientDTO(Klient k) {
        this.id = k.getId();
        this.imie = k.getImie();
        this.nazwisko = k.getNazwisko();
        this.telefon = k.getTelefon();
        this.email = k.getEmail();

        this.add(linkTo(methodOn(KlientRestController.class).getById(k.getId())).withSelfRel());
        this.add(linkTo(methodOn(KlientRestController.class).getAll()).withRel("all-klienci"));
    }
}
