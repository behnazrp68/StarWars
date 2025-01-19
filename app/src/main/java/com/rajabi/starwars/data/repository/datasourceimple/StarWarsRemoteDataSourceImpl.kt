package com.rajabi.starwars.data.repository.datasourceimple

import com.rajabi.starwars.data.model.search.Character
import com.rajabi.starwars.data.model.detail.CharacterDetailsResponse
import com.rajabi.starwars.data.model.detail.Films
import com.rajabi.starwars.data.model.detail.Planets
import com.rajabi.starwars.data.model.detail.Species
import com.rajabi.starwars.data.model.search.CharactersResponse
import com.rajabi.starwars.data.network.SwApiService
import com.rajabi.starwars.data.repository.datasource.StarWarsRemoteDataSource
import com.rajabi.starwars.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class StarWarsRemoteDataSourceImpl(private val apiService: SwApiService) :
    StarWarsRemoteDataSource {

    override suspend fun searchCharacter(name: String): Response<CharactersResponse> =
        apiService.searchCharacters(name)

    override suspend fun getCharacterDetailsByUrl(url: String): retrofit2.Response<CharacterDetailsResponse> =
        apiService.getCharacterDetailByUrl(url)

    override suspend fun getFilmsByUrl(url: String): retrofit2.Response<Films> =
        apiService.getFilmsDetailByUrl(url)

    override suspend fun getSpeciesByUrl(url: String): retrofit2.Response<Species> =
        apiService.getSpeciesDetailByUrl(url)

    override suspend fun getPlanetsByUrl(url: String): retrofit2.Response<Planets> =
        apiService.getPlanetsDetailByUrl(url)
}


