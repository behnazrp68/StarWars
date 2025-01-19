package com.rajabi.starwars.domain

import com.google.gson.annotations.SerializedName

data class ComprehensiveDetailModel(

    val name: String,
    val birthYear: String,
    val height: String,
    val nameSpecies: String,
    val language: String,
    val homeWorld: String,
    val population: String,
    val filmsTitle: List<String>,
    val openingCrawl: String

    )
