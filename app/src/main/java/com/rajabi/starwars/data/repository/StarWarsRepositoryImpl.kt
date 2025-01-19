package com.rajabi.starwars.data.repository

import com.rajabi.starwars.data.model.search.Character
import com.rajabi.starwars.data.model.detail.CharacterDetailsResponse
import com.rajabi.starwars.data.model.detail.Films
import com.rajabi.starwars.data.model.detail.Planets
import com.rajabi.starwars.data.model.detail.Species
import com.rajabi.starwars.data.repository.datasource.StarWarsRemoteDataSource
import com.rajabi.starwars.domain.repository.StarWarsRepository
import com.rajabi.starwars.util.Resource
import com.rajabi.starwars.util.UnifiedModelMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class StarWarsRepositoryImpl @Inject constructor(
    private val remoteDataSource: StarWarsRemoteDataSource,
    private val mapper: UnifiedModelMapper

) : StarWarsRepository {
    override suspend fun searchCharacter(name: String): Flow<Resource<List<Character>>> =
        remoteDataSource.searchCharacter(name)

    override suspend fun getCharacterDetails(id: String): Flow<Resource<CharacterDetailsResponse>> =
        remoteDataSource.getCharacterDetails(id)

    override suspend fun getCharacterDetailsByUrl(url: String): Flow<Resource<CharacterDetailsResponse>>  = flow { // Replace `Any` with the appropriate type
        emit(Resource.Loading) // Emit loading state

        val response = remoteDataSource.getCharacterDetailsByUrl(url)

        if (response.isSuccessful) {
            val result = response.body()!!
            emit(Resource.Success(result))
        } else {
            emit(Resource.Failure("Error: ${response.message()}"))
        }
    }.catch { e ->
        emit(Resource.Failure("Exception occurred"))
        e.printStackTrace()
    }.flowOn(Dispatchers.IO)

    override suspend fun getFilmsByUrl(url: String): Flow<Resource<Films>> {
        return remoteDataSource.getFilmsByUrl(url)
    }

    override suspend fun getSpeciesByUrl(url: String): Flow<Resource<Species>> {
        return remoteDataSource.getSpeciesByUrl(url)
    }

    override suspend fun getPlanetsByUrl(url: String): Flow<Resource<Planets>> {
        return remoteDataSource.getPlanetsByUrl(url)
    }


}