package com.rajabi.starwars.presentation.compose.detail

sealed class DetailEvent {
    data class ShowError(val message: String) : DetailEvent()
}