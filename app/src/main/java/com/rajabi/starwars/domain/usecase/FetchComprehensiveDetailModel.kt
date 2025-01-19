package com.rajabi.starwars.domain.usecase

import com.rajabi.starwars.domain.model.ComprehensiveDetailModel
import com.rajabi.starwars.domain.repository.StarWarsRepository

class FetchComprehensiveDetailModel(
    private val repository: StarWarsRepository
) {
//    suspend fun execute () : ComprehensiveDetailModel{
////        val apiFilms = repository.getFilmsByUrl()
//    }
}