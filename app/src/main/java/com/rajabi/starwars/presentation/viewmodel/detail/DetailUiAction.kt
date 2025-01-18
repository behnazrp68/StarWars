package com.rajabi.starwars.presentation.viewmodel.detail

import com.rajabi.starwars.data.model.Character

sealed class DetailAction {
    data class LoadCharacterDetails(val id: String) : DetailAction()
}