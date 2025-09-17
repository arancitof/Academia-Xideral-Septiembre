package com.Bancos.CRUD.service;

import com.Bancos.CRUD.model.Banco;
import com.Bancos.CRUD.repository.BancoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BancoServiceImpl implements BancoService{

    @Autowired
    private BancoRepository bancoRepository;

    public Banco save(Banco banco){
        if(banco.getFechaCreacion() == null){
            banco.setFechaCreacion(LocalDateTime.now());

        }
        return bancoRepository.save(banco);
    }

    @Override
    public List<Banco> findall(){
        return bancoRepository.findAll();
    }

    @Override
    public Optional<Banco> findById(String id){
        return bancoRepository.findById(id);
    }

    @Override
    public void deleteById(String id){
        bancoRepository.deleteById(id);
    }

    @Override
    public Banco update(String id, Banco banco){
        Optional<Banco> existingBanco = bancoRepository.findById(id);
        if(existingBanco.isPresent()){
            Banco bancoActualizado = existingBanco.get();
            bancoActualizado.setNombre(banco.getNombre());
            bancoActualizado.setEmail(banco.getEmail());
            bancoActualizado.setTelefono(banco.getTelefono());
            bancoActualizado.setOrigen(banco.getOrigen());
            return bancoRepository.save(bancoActualizado);
        }
        throw new RuntimeException("Banco no encontrado con el ID: " + id);
    }









}
