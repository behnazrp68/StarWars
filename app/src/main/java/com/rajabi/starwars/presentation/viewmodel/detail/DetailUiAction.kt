package com.rajabi.starwars.presentation.viewmodel.detail

sealed class DetailAction {
    data class LoadCharacterDetails(val id: String) : DetailAction()
}