package com.example.academypokemon.business.abstracts;

import com.example.academypokemon.entities.Pokemon;
import com.example.academypokemon.entities.PokemonWithAbility;

import java.util.List;

public interface PokemonService {
    /**
     * Returns all pokemon as list
     * @return List<Pokemon>
     */
    List<Pokemon> getAll();

    /**
     * Returns a pokemon that is being queried by using id
     * @param id
     * @return PokemonWithAbility
     */
    PokemonWithAbility getPokemonWithAbilityById(int id);
}
