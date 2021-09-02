package com.example.academypokemon.entities.pokemonDetails;

/**
 * Class corresponding to the ability portion of the JSON data
 */
public class Ability {
    public AbilityInner ability;
    public boolean is_hidden;
    public int slot;

    public String getAbilityName() {
        return ability.name;
    }
}

class AbilityInner{
    public String name;
    public String url;
}
