package com.javatechie.spring.batch.config;

import com.javatechie.spring.batch.entity.Pokemon;
import org.springframework.batch.item.ItemProcessor;

public class PokemonProcessor implements ItemProcessor<Pokemon, Pokemon> {

    @Override
    public Pokemon process(Pokemon pokemon) throws Exception {
        if(pokemon.getTipoPrincipal().equals("Agua") &&
           pokemon.getFoundRegion().equals("Kanto")) {
            return pokemon;
        }else{
            return null;
        }
    }
}
