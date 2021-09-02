package com.example.academypokemon.entities;

import java.util.List;

/**
 * Class corresponding to JSON data that gives list of all pokemon
 */
public class PokeApiResponse {
    public int count;
    public Object next;
    public Object previous;
    public List<Pokemon> results;
}
