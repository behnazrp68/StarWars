package com.rajabi.starwars.presentation.compose.search

sealed class SearchUiEvent {
    data class ShowToast(val message: String) : SearchUiEvent()
}