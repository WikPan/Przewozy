package com.example.przewozy.repo;

import com.example.przewozy.entity.Przewoz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrzewozRepository extends JpaRepository<Przewoz, Long> {
}
