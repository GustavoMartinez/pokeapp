package com.app.pokeapp.ui.screens

import PokemonFavoritesViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.app.pokeapp.viewmodel.PokemonListViewModel


/**
 * Componente que muestra la lista de favoritos
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonFavoritesScreen(
    navController: NavController,
    favoritesViewModel: PokemonFavoritesViewModel,
    viewModel: PokemonListViewModel = viewModel()
) {

    val pokemonList = viewModel.pokemonList
    val favoriteNames by favoritesViewModel.favorites.collectAsState()

    val favorites = remember(pokemonList, favoriteNames) {
        pokemonList.filter { it.name in favoriteNames }
    }


    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Favoritos") })
        }
    ) { padding ->
        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay Pokèmon favoritos aún.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(favorites) { pokemon ->
                    PokemonItem(
                        pokemon = pokemon,
                        navController = navController,
                        favoritesViewModel = favoritesViewModel
                    )
                }
            }
        }
    }
}


