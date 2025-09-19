package com.javatechie.spring.batch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "POKEMONS_INFO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pokemon {

    @Id
    @Column(name = "POKEMON_ID")
    private int id;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "REGION")
    private String foundRegion;
    @Column(name = "TIPO_Principal")
    private String tipoPrincipal;
    @Column(name = "DEBILIDAD_Principal")
    private String debilidadPrincipal;


}
