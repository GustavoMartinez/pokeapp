package com.app.pokeapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.app.pokeapp.ui.login.LoginActivity
import com.app.pokeapp.ui.screens.HomeScreen
import com.app.pokeapp.ui.screens.PokemonDetailScreen
import com.app.pokeapp.ui.screens.StatisticsScreen
import com.app.pokeapp.ui.screens.StatsScreen
import com.app.pokeapp.util.SessionManager

/**
 * Clase principal de la app, que contiene la esctrucura base
 */
class MainActivity : ComponentActivity() {
    /**
     * Cierra la autenticación
     */
    private fun logout() {
        SessionManager.logout(this)
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") {
                        HomeScreen(navController = navController, onLogout = { logout() })
                    }
                    composable(
                        "detail/{pokemonName}",
                        arguments = listOf(navArgument("pokemonName") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val pokemonName = backStackEntry.arguments?.getString("pokemonName") ?: ""
                        PokemonDetailScreen(pokemonName = pokemonName) {
                            navController.popBackStack()
                        }
                    }
                    composable("stats") {
                        StatisticsScreen (
                            favoritesViewModel = viewModel(),
                            onBack = { navController.popBackStack() }
                        )


                    }

                }
            }
        }
    }
}
