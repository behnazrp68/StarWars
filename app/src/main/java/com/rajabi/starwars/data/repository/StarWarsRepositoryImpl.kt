package com.rajabi.starwars.data.repository

import com.rajabi.starwars.data.model.search.Character
import com.rajabi.starwars.data.model.detail.CharacterDetailsResponse
import com.rajabi.starwars.data.repository.datasource.StarWarsRemoteDataSource
import com.rajabi.starwars.domain.repository.StarWarsRepository
import com.rajabi.starwars.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StarWarsRepositoryImpl @Inject constructor(
    private val remoteDataSource: StarWarsRemoteDataSource
) : StarWarsRepository {
    override suspend fun searchCharacter(name: String): Flow<Resource<List<Character>>> =
        remoteDataSource.searchCharacter(name)

    override suspend fun getCharacterDetails(id: String): Flow<Resource<CharacterDetailsResponse>> =
        remoteDataSource.getCharacterDetails(id)

    override suspend fun getResourceByUrl(url: String): Flow<Resource<CharacterDetailsResponse>> {
       return remoteDataSource.getResourceByUrl(url)
    }


}