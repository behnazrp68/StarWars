package com.rajabi.starwars.presentation.viewmodel.search

sealed class SearchUiAction {
    data class Search(val query: String) : SearchUiAction()
}