package com.app.pokeapp.util

import android.content.Context
import android.content.SharedPreferences

/**
 * Clase que contiene funciones para controlar los favoritos
 * @property PREF_NAME String estático que se usa en referencia a SharedPreferences
 * @property KEY_FAVORITES String estático que es la llave de un valor almacenado
 */
object FavoritesManager {
    private const val PREF_NAME = "favorites_prefs"
    private const val KEY_FAVORITES = "favorites"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getFavorites(context: Context): Set<String> {
        return getPrefs(context).getStringSet(KEY_FAVORITES, emptySet()) ?: emptySet()
    }

    fun toggleFavorite(context: Context, pokemonName: String) {
        val prefs = getPrefs(context)
        val current = getFavorites(context).toMutableSet()
        if (current.contains(pokemonName)) {
            current.remove(pokemonName)
        } else {
            current.add(pokemonName)
        }
        prefs.edit().putStringSet(KEY_FAVORITES, current).apply()
    }

    fun isFavorite(context: Context, pokemonName: String): Boolean {
        return getFavorites(context).contains(pokemonName)
    }
}


