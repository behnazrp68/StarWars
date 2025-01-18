package com.rajabi.starwars.presentation.viewmodel.detail

import com.rajabi.starwars.data.model.detail.CharacterDetailsResponse
import com.rajabi.starwars.util.Resource

sealed class DetailUiState {
    data object Idle : DetailUiState()
    data object Loading : DetailUiState()
    data class Success(val details: CharacterDetailsResponse) : DetailUiState()
    data class Error(val message: String) : DetailUiState()}