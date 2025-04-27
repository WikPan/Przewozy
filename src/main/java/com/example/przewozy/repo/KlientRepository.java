package com.example.przewozy.repo;
import com.example.przewozy.entity.Klient;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
public interface KlientRepository extends JpaRepository<Klient, Long> {
    List<Klient> findByNazwisko(String nazwisko);
}

