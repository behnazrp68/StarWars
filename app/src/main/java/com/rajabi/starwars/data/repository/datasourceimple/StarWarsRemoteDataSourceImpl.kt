package com.rajabi.starwars.data.repository.datasourceimple

import com.rajabi.starwars.data.model.Character
import com.rajabi.starwars.data.model.detail.CharacterDetailsResponse
import com.rajabi.starwars.data.network.SwApiService
import com.rajabi.starwars.data.repository.datasource.StarWarsRemoteDataSource
import com.rajabi.starwars.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow

class StarWarsRemoteDataSourceImpl(private val apiService: SwApiService) :
    StarWarsRemoteDataSource {
    override suspend fun searchCharacter(name: String): Flow<Resource<List<Character>>> = flow {
        emit(Resource.Loading) // Emit loading state

        if (name.isBlank()) {
            emit(Resource.Success(emptyList())) // Emit empty list for empty query
            return@flow
        }

        val response = apiService.searchCharacters(name)
        if (response.isSuccessful) {
            val results = response.body()?.characters ?: emptyList()
            emit(Resource.Success(results))
        } else {
            emit(Resource.Failure("Error: ${response.message()}"))
        }
    }.catch { e ->
        emit(Resource.Failure("Exception occurred"))
        e.printStackTrace()
    }

    override suspend fun getCharacterDetails(id: String): Flow<Resource<CharacterDetailsResponse>> =
        flow {

            emit(Resource.Loading) // Emit loading state


            val response = apiService.getCharacterDetails(id)

            if (response.isSuccessful) {
                val results = response.body()!!

                emit(Resource.Success(results))
            } else {
                emit(Resource.Failure("Error: ${response.message()}"))
            }
        }.catch { e ->
            emit(Resource.Failure("Exception occurred"))
            e.printStackTrace()
        }

    override suspend fun getResourceByUrl(url: String): Flow<Resource<CharacterDetailsResponse>> = flow { // Replace `Any` with the appropriate type
        emit(Resource.Loading) // Emit loading state

        val response = apiService.getResourceByUrl(url)

        if (response.isSuccessful) {
            val result = response.body()!!
            emit(Resource.Success(result))
        } else {
            emit(Resource.Failure("Error: ${response.message()}"))
        }
    }.catch { e ->
        emit(Resource.Failure("Exception occurred"))
        e.printStackTrace()
    }

}


