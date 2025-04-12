package com.app.pokeapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.pokeapp.viewmodel.StatisticsViewModel
import PokemonFavoritesViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    favoritesViewModel: PokemonFavoritesViewModel,
    statisticsViewModel: StatisticsViewModel = viewModel(),
    onBack: () -> Unit
) {
    val favoritesCount by statisticsViewModel.favoritesCount.collectAsState()
    val seenCount by statisticsViewModel.seenCount.collectAsState()
    val sessionTime by statisticsViewModel.sessionTime.collectAsState()

    LaunchedEffect(favoritesViewModel.favorites) {
        statisticsViewModel.updateFavoritesCount(favoritesViewModel.favorites.value.size)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Estadísticas") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text("Pokémon favoritos: $favoritesCount")
            Text("Pokémon vistos: $seenCount")
            Text("Tiempo en la app: ${sessionTime / 1000}s")
        }
    }
}
