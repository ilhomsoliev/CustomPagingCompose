package com.ilhomsoliev.paging.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilhomsoliev.paging.data.repository.Repository
import com.ilhomsoliev.paging.paging.PagingState
import com.ilhomsoliev.paging.presentation.model.CharacterModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: Repository,
) : ViewModel() {

    private val _charactersPagingState = MutableStateFlow(PagingState<CharacterModel>())
    val charactersPagingState = _charactersPagingState.asStateFlow()

    init {
        viewModelScope.launch {
            initPaging()
        }
    }
    suspend fun initPaging(){
        _charactersPagingState.emit(_charactersPagingState.value.copy(onUpdate = {
            _charactersPagingState.emit(
                it
            )
        }))

        _charactersPagingState.emit(_charactersPagingState.value.copy(onFetchData = { page ->
            val result = repository.getCharacters(
                page = page,
            )
            result
        }))


        _charactersPagingState.emit(_charactersPagingState.value.copy(onError = { cause: String? ->

        }))
        onNewPosition(0)
    }

    suspend fun onNewPosition(index: Int) {
        _charactersPagingState.value.onNewPosition(index)
    }

}