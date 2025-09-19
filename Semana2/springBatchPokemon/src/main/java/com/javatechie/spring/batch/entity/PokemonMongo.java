package com.javatechie.spring.batch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "pokes")
public class PokemonMongo {

    @Id
    private String id;
    private Integer pokemonId;
    private String name;
    private String foundRegion;
    private String tipo;
    private String debilidad;

}