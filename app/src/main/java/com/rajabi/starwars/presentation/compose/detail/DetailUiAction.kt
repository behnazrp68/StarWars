package com.rajabi.starwars.presentation.compose.detail

sealed class DetailAction {
    data class LoadCharacterDetails(val id: String) : DetailAction()
}