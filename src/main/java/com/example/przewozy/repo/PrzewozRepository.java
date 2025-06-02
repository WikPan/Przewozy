package com.example.przewozy.repo;

import com.example.przewozy.entity.Przewoz;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PrzewozRepository extends CrudRepository<Przewoz, Integer> {
	List<Przewoz> findByTrasaId(Integer trasaId);
	List<Przewoz> findByAutobusId(Integer autobusId);
}
