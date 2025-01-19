package com.rajabi.starwars.util

import com.rajabi.starwars.data.model.detail.Films
import com.rajabi.starwars.data.model.detail.Planets
import com.rajabi.starwars.data.model.detail.Species
import com.rajabi.starwars.domain.model.ComprehensiveDetailModel

class UnifiedModelMapper {
    fun Films.toUnifiedModel(): ComprehensiveDetailModel {
        return ComprehensiveDetailModel(

            name = "",
            filmsTitle = listOf(title),
            openingCrawl = openingCrawl,
            height = "",
            language = "",
            birthYear = ""
        )
    }

    fun Species.toUnifiedModel(): ComprehensiveDetailModel {
        return ComprehensiveDetailModel(
            homeWorld = homeWorld,
            language = language
        )
    }

    fun Planets.toUnifiedModel(): ComprehensiveDetailModel {
        return ComprehensiveDetailModel(
            population = population
        )
    }
}
