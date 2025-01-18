package com.rajabi.starwars.domain.repository

import com.rajabi.starwars.data.model.Character
import com.rajabi.starwars.data.model.detail.CharacterDetailsResponse
import com.rajabi.starwars.util.Resource
import kotlinx.coroutines.flow.Flow

interface StarWarsRepository {
    suspend fun searchCharacter(name: String) :Flow<Resource<List<Character>>>
    suspend fun getCharacterDetails(id:String):Flow<Resource<CharacterDetailsResponse>>
    suspend fun getResourceByUrl(url:String):Flow<Resource<CharacterDetailsResponse>>
}