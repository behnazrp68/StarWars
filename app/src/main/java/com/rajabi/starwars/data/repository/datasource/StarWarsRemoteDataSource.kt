package com.rajabi.starwars.data.repository.datasource

import com.rajabi.starwars.data.model.search.Character
import com.rajabi.starwars.data.model.detail.CharacterDetailsResponse
import com.rajabi.starwars.util.Resource
import kotlinx.coroutines.flow.Flow

interface StarWarsRemoteDataSource {
    suspend fun searchCharacter(name: String): Flow<Resource<List<Character>>>

    suspend fun getCharacterDetails(id: String) : Flow<Resource<CharacterDetailsResponse>>
    suspend fun getResourceByUrl(url: String): Flow<Resource<CharacterDetailsResponse>>
}