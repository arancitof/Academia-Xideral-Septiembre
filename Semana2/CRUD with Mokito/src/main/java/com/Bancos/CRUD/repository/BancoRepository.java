package com.Bancos.CRUD.repository;

import com.Bancos.CRUD.model.Banco;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BancoRepository extends MongoRepository<Banco, String> {
}
