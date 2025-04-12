package com.app.pokeapp.data.models

/**
 * Clase que crea un pokemon
 * @property name String nombre del pokemon
 * @property url String que contiene el id para obtener la imagen
 */
data class PokemonEntry(
    val name: String,
    val url: String
)