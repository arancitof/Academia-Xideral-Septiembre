package com.javatechie.spring.batch.config;

import com.javatechie.spring.batch.entity.Pokemon;
import com.javatechie.spring.batch.entity.PokemonMongo;
import org.springframework.batch.item.ItemProcessor;

public class PokemonNameProcessor implements ItemProcessor<Pokemon, PokemonMongo> {

    @Override
    public PokemonMongo process(Pokemon pokemon) throws Exception {
        PokemonMongo pokemonMongo = new PokemonMongo();

        //Mapear Campos
        pokemonMongo.setPokemonId(pokemon.getId());
        pokemonMongo.setName(pokemon.getNombre());
        pokemonMongo.setFoundRegion(pokemon.getFoundRegion());
        pokemonMongo.setTipo(pokemon.getTipoPrincipal());
        pokemonMongo.setDebilidad(pokemon.getDebilidadPrincipal());



        String nombreFinal;
        if (pokemon.getNombre() != null){
            nombreFinal = pokemon.getNombre() + " Pokemon Generico";
        } else {
            nombreFinal = "Pokemon Generico";
        }
        pokemonMongo.setName(nombreFinal);

        return pokemonMongo;
    }
}