package com.app.pokeapp.ui.screens

import PokemonFavoritesViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

/**
 * Componente que contiene los 2 tabs principales en donde se muestran las listas
 * de pokemones y de favoritos
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, onLogout: () -> Unit) {
    val tabs = listOf("Todos", "Favoritos")
    var selectedTabIndex by remember { mutableStateOf(0) }
    val favoritesViewModel: PokemonFavoritesViewModel = viewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PokeApp") },
                actions = {
                    IconButton(onClick = { onLogout() }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index }
                    )
                }
            }

            when (selectedTabIndex) {
                0 -> PokemonListScreen(navController = navController)
                1 -> PokemonFavoritesScreen(
                    navController = navController,
                    favoritesViewModel = favoritesViewModel
                )
            }
        }
    }
}


