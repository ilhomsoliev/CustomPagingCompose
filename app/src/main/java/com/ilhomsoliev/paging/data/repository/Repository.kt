package com.ilhomsoliev.paging.data.repository

import com.ilhomsoliev.paging.presentation.model.CharacterModel
import com.ilhomsoliev.paging.data.network.model.character.CharacterResponse
import com.ilhomsoliev.rikandmortytest.data.network.model.episode.EpisodeResponse
import com.ilhomsoliev.rikandmortytest.data.network.model.location.LocationResponse

interface Repository {
    suspend fun getLocations(): List<LocationResponse>
    suspend fun getCharacters(page:Int): List<CharacterModel>
    suspend fun getCharacterByRequest(request: String): List<CharacterResponse>
    suspend fun getCharacterById(id: Int): CharacterResponse
    suspend fun getLocationById(id: Int): LocationResponse
    suspend fun getEpisodesByRequest(request: String): List<EpisodeResponse>
}