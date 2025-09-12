package com.Bancos.CRUD.rest;


import com.Bancos.CRUD.model.Banco;
import com.Bancos.CRUD.service.BancoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bancos")
public class BancoRest {

    @Autowired
    private BancoService bancoService;

    @PostMapping
    public ResponseEntity<Banco> createBanco(@RequestBody Banco banco){
        try{
            Banco nuevoBanco = bancoService.save(banco);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoBanco);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping
    public ResponseEntity<List<Banco>> getallBancos(){
        try{
            List<Banco> bancos = bancoService.findall();
            return ResponseEntity.ok(bancos);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Banco> getBancoById(@PathVariable String id){
        try{
            Optional<Banco> banco = bancoService.findById(id);
            return banco.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Banco> updateBanco(@PathVariable String id, @RequestBody Banco banco){
        try{
            Banco bancoActualizado = bancoService.update(id, banco);
            return ResponseEntity.ok(bancoActualizado);
    } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanco(@PathVariable String id){
        try{
            if (bancoService.findById(id).isPresent()){
                bancoService.deleteById(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}
