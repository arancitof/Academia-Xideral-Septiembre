package com.javatechie.spring.batch.repository;

import com.javatechie.spring.batch.entity.PokemonMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PokemonMongoRepository extends MongoRepository<PokemonMongo, String> {
}