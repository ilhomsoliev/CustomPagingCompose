package com.ilhomsoliev.paging.data.repository

import com.ilhomsoliev.paging.data.network.ServerApi
import com.ilhomsoliev.paging.presentation.model.CharacterModel
import com.ilhomsoliev.paging.presentation.model.map
import com.ilhomsoliev.paging.data.network.model.character.CharacterResponse
import com.ilhomsoliev.rikandmortytest.data.network.model.episode.EpisodeResponse
import com.ilhomsoliev.rikandmortytest.data.network.model.location.LocationResponse

class RepositoryImpl(
    private val api: ServerApi
) : Repository {

    override suspend fun getLocations(): List<LocationResponse> =
        api.getLocation().results

    override suspend fun getCharacters(page: Int): List<CharacterModel> =
        api.getCharacters(page).results.map { it.map() }

    override suspend fun getCharacterByRequest(request: String): List<CharacterResponse> =
        api.getCharacterByRequest(request)

    override suspend fun getCharacterById(id: Int): CharacterResponse = api.getCharacterById(id)

    override suspend fun getLocationById(id: Int): LocationResponse = api.getLocationById(id)

    override suspend fun getEpisodesByRequest(request: String): List<EpisodeResponse> =
        api.getEpisodes(request)

}