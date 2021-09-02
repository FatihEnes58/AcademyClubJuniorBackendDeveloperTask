package com.example.academypokemon.entities.pokemonDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Class corresponding to JSON data from pokemon's URL information
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonDetails {
    public List<Ability> abilities;
}
