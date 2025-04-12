package com.app.pokeapp.util

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("favorites_prefs")

class FavoritesRepository(private val context: Context) {
    companion object {
        private val FAVORITES_KEY = stringSetPreferencesKey("favorite_pokemon_names")
    }

    val favorites: Flow<Set<String>> = context.dataStore.data.map { prefs ->
        prefs[FAVORITES_KEY] ?: emptySet()
    }

    suspend fun saveFavorites(favorites: Set<String>) {
        context.dataStore.edit { prefs ->
            prefs[FAVORITES_KEY] = favorites
        }
    }
}
