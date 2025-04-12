package com.app.pokeapp.data.models

/**
 * Lista que contiene varios tipos de PokemonEntry
 * @see PokemonEntry
 */
data class PokemonListResponse(
    val results: List<PokemonEntry>
)