package com.rajabi.starwars.util

import com.rajabi.starwars.data.model.detail.CharacterDetailsResponse
import com.rajabi.starwars.data.model.detail.Films
import com.rajabi.starwars.data.model.detail.Planets
import com.rajabi.starwars.data.model.detail.Species
import com.rajabi.starwars.domain.model.ComprehensiveDetailModel

class UnifiedModelMapper {
    fun mapToComprehensiveDetailModel(
        characterDetails: CharacterDetailsResponse,
        films: List<Films>,
        species: List<Species>,
        planet: Planets?
    ): ComprehensiveDetailModel {
        return ComprehensiveDetailModel(
            name = characterDetails.name,
            filmsTitle = films.map { it.title },
            openingCrawl = films.map {   it.opening_crawl },
            height = characterDetails.height,
            language = species.joinToString(separator = ", ") { it.language },
            birthYear = characterDetails.birthYear,
            population = planet?.population!!,
            homeWorld = species.joinToString(separator = " "){it.homeWorld},
            nameSpecies = species.joinToString(separator = " "){it.name},

        )
    }
}