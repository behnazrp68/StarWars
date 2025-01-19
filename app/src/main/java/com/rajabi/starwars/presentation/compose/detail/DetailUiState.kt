package com.rajabi.starwars.presentation.compose.detail

import com.rajabi.starwars.domain.model.ComprehensiveDetailModel

sealed class DetailUiState {
    data object Idle : DetailUiState()
    data object Loading : DetailUiState()
    data class Success(val details: ComprehensiveDetailModel) : DetailUiState()
    data class Error(val message: String) : DetailUiState()}