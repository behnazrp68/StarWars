package com.rajabi.starwars.domain.repository

import com.rajabi.starwars.data.model.search.Character
import com.rajabi.starwars.data.model.detail.CharacterDetailsResponse
import com.rajabi.starwars.data.model.detail.Films
import com.rajabi.starwars.data.model.detail.Planets
import com.rajabi.starwars.data.model.detail.Species
import com.rajabi.starwars.domain.model.ComprehensiveDetailModel
import com.rajabi.starwars.util.Resource
import kotlinx.coroutines.flow.Flow

interface StarWarsRepository {
    suspend fun searchCharacter(name: String) :Flow<Resource<List<Character>>>
    suspend fun getCharacterDetailsByUrl(url:String):Flow<Resource<ComprehensiveDetailModel>>
    suspend fun getCharacterDetails(id:String):Flow<Resource<CharacterDetailsResponse>>


}