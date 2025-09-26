package com.Bancos.CRUD.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

@Document(collection = "bancos")
public class Banco {

    @Id
    private String id;

    private String nombre;

    @Indexed(unique = true)
    private String email;

    private String telefono;

    private String origen;

    private LocalDateTime fechaCreacion;

    public Banco(){
        this.fechaCreacion = LocalDateTime.now();
    }

    public Banco(String nombre, String email, String telefono, String origen){
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.origen = origen;
        this.fechaCreacion = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
