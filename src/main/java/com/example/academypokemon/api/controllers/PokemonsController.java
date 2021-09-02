package com.example.academypokemon.api.controllers;

import com.example.academypokemon.business.abstracts.PokemonService;
import com.example.academypokemon.entities.Pokemon;
import com.example.academypokemon.entities.PokemonWithAbility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pokemons")
public class PokemonsController {

    private PokemonService pokemonService;

    @Autowired
    public PokemonsController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("/getall")
    public List<Pokemon> getAll() {
        return pokemonService.getAll();
    }

    @GetMapping("/get-pokemon-with-ability-by-id")
    public PokemonWithAbility getPokemonWithAbilityById(@RequestParam("pokemonId") int pokemonId) {
        return pokemonService.getPokemonWithAbilityById(pokemonId);
    }
}
