@file:OptIn(ExperimentalGlideComposeApi::class)

package com.app.pokeapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.pokeapp.data.models.PokemonResponse
import com.app.pokeapp.data.network.RetrofitInstance
import com.app.pokeapp.viewmodel.StatisticsViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Componente que muestra en una nueva pantalla al pokemon y sus habilidades
 * @property pokemonName String del nombre del pokemon
 * @property onBack función para volver a la lista principal
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(pokemonName: String, onBack: () -> Unit) {
    var pokemon by remember { mutableStateOf<PokemonResponse?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    val statisticsViewModel: StatisticsViewModel = viewModel()

    LaunchedEffect(pokemonName) {
        RetrofitInstance.api.getPokemon(pokemonName).enqueue(object : Callback<PokemonResponse> {
            override fun onResponse(call: Call<PokemonResponse>, response: Response<PokemonResponse>) {
                if (response.isSuccessful) {
                    pokemon = response.body()
                    statisticsViewModel.registerPokemonSeen() // aquí aumentamos el contador
                } else {
                    error = "Error: ${response.code()}"
                }
                isLoading = false
            }

            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                error = "Fallo de red: ${t.message}"
                isLoading = false
            }
        })
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text(pokemonName.uppercase()) }, navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
        })
    }) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
            when {

                isLoading -> CircularProgressIndicator()
                error != null -> Text(error ?: "", color = Color.Red)
                pokemon != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        GlideImage(
                            model = pokemon!!.sprites.other.officialArtwork.frontDefault,
                            contentDescription = pokemon!!.name,
                            modifier = Modifier.size(150.dp),

                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text("Habilidades", style = MaterialTheme.typography.titleMedium)

                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment =  Alignment.Center
                        ) {
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                items(pokemon!!.abilities) { ability ->
                                    AssistChip(
                                        onClick = {  },
                                        label = {
                                            Text(ability.ability.name.replaceFirstChar { it.uppercase() })
                                        }
                                    )
                                }
                            }
                        }


                    }
                }
            }
        }
    }
}
