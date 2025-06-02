package com.example.przewozy.config;

import com.example.przewozy.entity.*;
import com.example.przewozy.enums.StatusBiletu;
import com.example.przewozy.repo.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AutobusRepository autobusRepository;
    private final TrasaRepository trasaRepository;
    private final PrzewozRepository przewozRepository;
    private final KlientRepository klientRepository;
    private final BiletRepository biletRepository;

    @Override
    @Transactional
    public void run(String... args) {
        // Autobus
        Autobus autobus = new Autobus();
        autobus.setMarka("Mercedes");
        autobus.setModel("Sprinter");
        autobus.setLiczbaMiejsc(20);
        autobus.setRokProdukcji(2019);
        autobus = autobusRepository.save(autobus);

        // Trasa
        Trasa trasa = new Trasa();
        trasa.setPunktStartowy("Warszawa");
        trasa.setPunktDocelowy("Kraków");
        trasa.setDystansKm(300);
        trasa = trasaRepository.save(trasa);

        // Przewóz powiązany z autobusem i trasą
        Przewoz przewoz = new Przewoz();
        przewoz.setData(LocalDate.now().plusDays(1));
        przewoz.setGodzina(LocalTime.of(10, 0));
        przewoz.setAutobus(autobus);
        przewoz.setTrasa(trasa);
        przewoz = przewozRepository.save(przewoz);

        // Przewóz powiązany z autobusem i trasą
        Przewoz przewoz1 = new Przewoz();
        przewoz1.setData(LocalDate.now().minusDays(1));
        przewoz1.setGodzina(LocalTime.of(10, 0));
        przewoz1.setAutobus(autobus);
        przewoz1.setTrasa(trasa);
        przewoz1 = przewozRepository.save(przewoz1);

        // Klient
        Klient klient = new Klient();
        klient.setImie("Jan");
        klient.setNazwisko("Kowalski");
        klient.setTelefon("123456789");
        klient.setEmail("jan.kowalski@example.com");
        klient = klientRepository.save(klient);

        // Bilet przypisany do przewozu i klienta
        Bilet bilet = new Bilet();
        bilet.setCena(100);
        bilet.setMiejsce("1A");
        bilet.setStatus(StatusBiletu.AKTYWNY);
        bilet.setKlient(klient);
        bilet.setPrzewoz(przewoz);
        biletRepository.save(bilet);
    }
}
