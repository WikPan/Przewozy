package com.example.przewozy.repo;

import com.example.przewozy.entity.Przewoz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PrzewozRepository extends CrudRepository<Przewoz, Integer> {
}
