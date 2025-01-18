package com.rajabi.starwars.presentation.viewmodel.search

import com.rajabi.starwars.data.model.Character

sealed class SearchUiState {
    data object Idle : SearchUiState()
    data object Loading : SearchUiState()
    data class Success(val characters: List<Character>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}
