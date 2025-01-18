package com.rajabi.starwars.presentation.viewmodel.detail

sealed class DetailEvent {
    data class ShowError(val message: String) : DetailEvent()
}