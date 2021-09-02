package com.example.academypokemon.business.concretes;

import com.example.academypokemon.business.abstracts.PokemonService;
import com.example.academypokemon.entities.PokeApiResponse;
import com.example.academypokemon.entities.Pokemon;
import com.example.academypokemon.entities.PokemonWithAbility;
import com.example.academypokemon.entities.pokemonDetails.Ability;
import com.example.academypokemon.entities.pokemonDetails.PokemonDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class PokemonManager implements PokemonService {

    /**
     * Returns an HTTP Response that corresponds to the provided URL
     */
    private HttpResponse<String> getStringHttpResponseByUrl(final String url) {

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET().header("accept", "application/json")
                .uri(URI.create(url)).build();
        HttpResponse<String> httpResponse = null;
        try {
            httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return httpResponse;
    }

    /**
     * Returns an object from HTTP Response.
     * Type of this object is whatever you provide.
     *
     * @throws JsonProcessingException
     */
    private <T> T getObjectResponse(Class<T> clazz, String url) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(getStringHttpResponseByUrl(url).body(), clazz);
    }

    /**
     * Returns a pokemon list corresponds to the object response
     */
    private List<Pokemon> getAllPokemonAsList() {

        final String POSTS_API_URL = "https://pokeapi.co/api/v2/pokemon?limit=10000";
        PokeApiResponse pokeApiResponse = new PokeApiResponse();
        try {
            pokeApiResponse = getObjectResponse(PokeApiResponse.class, POSTS_API_URL);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(pokeApiResponse);
        return pokeApiResponse.results;
    }

    /**
     * Returns all pokemon as list
     *
     * @return List<Pokemon>
     */
    @Override
    public List<Pokemon> getAll() {
        return getAllPokemonAsList();
    }

    /**
     * Returns a pokemon that is being queried by using id
     *
     * @param id
     * @return PokemonWithAbility
     */
    @Override
    public PokemonWithAbility getPokemonWithAbilityById(int id) {
        List<Pokemon> pokemons = getAllPokemonAsList();
        Pokemon pokemonThatWanted = null;

        for (Pokemon pokemon : pokemons) {
            int currentPokemonId = Integer.parseInt(
                    pokemon.url.substring(34, pokemon.url.length() - 1));//Pokemon id starts at 34. index of url strings
            if (currentPokemonId == id) {
                pokemonThatWanted = pokemon;
            }
        }

        final String POSTS_API_URL = pokemonThatWanted.url;
        PokemonDetails pokemonDetails = new PokemonDetails();
        pokemonDetails = getPokemonDetails(POSTS_API_URL, pokemonDetails);
        List<String> abilityNames = getAbilityNames(pokemonDetails);
        PokemonWithAbility pokemonWithAbility = new PokemonWithAbility();
        pokemonWithAbility.name = pokemonThatWanted.name;
        pokemonWithAbility.abilityNames = abilityNames;

        return pokemonWithAbility;
    }

    /**
     * Returns ability names as a list of string.
     * Uses a PokemonDetails object to get the ability names of queried pokemon.
     *
     * @param pokemonDetails
     * @return List<String>
     */
    private List<String> getAbilityNames(PokemonDetails pokemonDetails) {
        List<String> abilityNames = new ArrayList();
        for (Ability ability : pokemonDetails.abilities) {
            abilityNames.add(ability.getAbilityName());
        }
        return abilityNames;
    }

    /**
     * Returns pokemon details of queried pokemon as a PokemonDetails object
     *
     * @param POSTS_API_URL
     * @param pokemonDetails
     * @return PokemonDetails
     */
    private PokemonDetails getPokemonDetails(String POSTS_API_URL, PokemonDetails pokemonDetails) {
        try {
            pokemonDetails = getObjectResponse(PokemonDetails.class, POSTS_API_URL);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return pokemonDetails;
    }
}
