package com.javatechie.spring.batch.repository;

import com.javatechie.spring.batch.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon,Integer> {
}
