package com.app.pokeapp.data.models

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    val name: String,
    val sprites: Sprites,
    val abilities: List<AbilitySlot>
)

data class Sprites(
    val other: OtherSprites
)

data class OtherSprites(
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork
)

data class OfficialArtwork(
    @SerializedName("front_default")
    val frontDefault: String
)

data class AbilitySlot(val ability: Ability)

data class Ability(val name: String)
