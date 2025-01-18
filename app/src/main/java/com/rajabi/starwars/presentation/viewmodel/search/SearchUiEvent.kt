package com.rajabi.starwars.presentation.viewmodel.search

sealed class SearchUiEvent {
    data class ShowToast(val message: String) : SearchUiEvent()
}