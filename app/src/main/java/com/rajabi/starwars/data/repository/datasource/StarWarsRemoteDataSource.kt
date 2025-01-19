package com.rajabi.starwars.data.repository.datasource

import com.rajabi.starwars.data.model.search.Character
import com.rajabi.starwars.data.model.detail.CharacterDetailsResponse
import com.rajabi.starwars.data.model.detail.Films
import com.rajabi.starwars.data.model.detail.Planets
import com.rajabi.starwars.data.model.detail.Species
import com.rajabi.starwars.util.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.Response

interface StarWarsRemoteDataSource {
    suspend fun searchCharacter(name: String): Flow<Resource<List<Character>>>

    suspend fun getCharacterDetails(id: String) : Flow<Resource<CharacterDetailsResponse>>
    suspend fun getCharacterDetailsByUrl(url: String): retrofit2.Response<CharacterDetailsResponse>
    suspend fun getFilmsByUrl(url: String): Flow<Resource<Films>>
    suspend fun getSpeciesByUrl(url: String): Flow<Resource<Species>>
    suspend fun getPlanetsByUrl(url: String): Flow<Resource<Planets>>


}