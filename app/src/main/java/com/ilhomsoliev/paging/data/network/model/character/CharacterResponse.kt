package com.ilhomsoliev.paging.data.network.model.character

import com.ilhomsoliev.rikandmortytest.data.network.model.character.Location
import com.ilhomsoliev.rikandmortytest.data.network.model.character.Origin

data class CharacterResponse(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)