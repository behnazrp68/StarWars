package com.rajabi.starwars.presentation.compose.search

sealed class SearchUiAction {
    data class Search(val query: String) : SearchUiAction()
}