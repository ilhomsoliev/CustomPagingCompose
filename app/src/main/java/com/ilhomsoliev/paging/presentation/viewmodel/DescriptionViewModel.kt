package com.ilhomsoliev.paging.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.ilhomsoliev.paging.data.network.model.character.CharacterResponse
import com.ilhomsoliev.paging.data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DescriptionViewModel(
    private val repository: Repository,
) : ViewModel() {

    private val _character = MutableStateFlow<CharacterResponse?>(null)
    val character = _character.asStateFlow()


    suspend fun loadCharacter(id: Int) {
        _character.emit(repository.getCharacterById(id))
    }

}