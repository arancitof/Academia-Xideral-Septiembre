package com.Bancos.CRUD.service;

import java.util.List;
import java.util.Optional;

import com.Bancos.CRUD.model.Banco;

public interface BancoService {

    Banco save(Banco banco);

    List<Banco> findall();

    Optional<Banco> findById(String id);

    void deleteById(String id);

    Banco update(String id, Banco banco);
}
