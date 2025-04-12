package com.app.pokeapp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.app.pokeapp.data.models.PokemonEntry
import com.app.pokeapp.data.models.PokemonListResponse
import com.app.pokeapp.data.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonListViewModel : ViewModel() {
    var pokemonList by mutableStateOf<List<PokemonEntry>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    private var currentOffset = 0

    init {
        loadMorePokemon(initial = true)
    }

    fun loadMorePokemon(limit: Int = 20, initial: Boolean = false) {
        if (isLoading) return
        isLoading = true
        errorMessage = null // Limpiar error anterior

        RetrofitInstance.api.getPokemonList(
            limit = if (initial) 20 else limit,
            offset = currentOffset
        ).enqueue(object : Callback<PokemonListResponse> {
            override fun onResponse(call: Call<PokemonListResponse>, response: Response<PokemonListResponse>) {
                isLoading = false
                if (response.isSuccessful) {
                    val newData = response.body()?.results ?: emptyList()
                    pokemonList = pokemonList + newData
                    currentOffset += if (initial) 20 else limit
                } else {
                    errorMessage = "Error al obtener los datos: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<PokemonListResponse>, t: Throwable) {
                isLoading = false
                errorMessage = "Error de red: ${t.message}"
            }
        })
    }

    fun retry() {
        loadMorePokemon(initial = pokemonList.isEmpty())
    }
}

