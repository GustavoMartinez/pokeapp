package com.app.pokeapp.data.repository

import com.app.pokeapp.data.models.PokemonListResponse
import com.app.pokeapp.data.models.PokemonResponse
import com.app.pokeapp.data.network.ApiService
import retrofit2.Call

class PokemonRepository(private val apiService: ApiService) {

    fun getPokemonList(limit: Int, offset: Int): Call<PokemonListResponse> {
        return apiService.getPokemonList(limit, offset)
    }

    fun getPokemon(name: String): Call<PokemonResponse> {
        return apiService.getPokemon(name)
    }
}
