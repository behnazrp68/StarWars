package com.rajabi.starwars.data.repository

import com.rajabi.starwars.data.model.search.Character
import com.rajabi.starwars.data.model.detail.CharacterDetailsResponse
import com.rajabi.starwars.data.model.detail.Films
import com.rajabi.starwars.data.model.detail.Planets
import com.rajabi.starwars.data.model.detail.Species
import com.rajabi.starwars.data.repository.datasource.StarWarsRemoteDataSource
import com.rajabi.starwars.domain.model.ComprehensiveDetailModel
import com.rajabi.starwars.domain.repository.StarWarsRepository
import com.rajabi.starwars.util.Resource
import com.rajabi.starwars.util.UnifiedModelMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
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
        flow {
            emit(Resource.Loading) // Emit loading state

            if (name.isBlank()) {
                emit(Resource.Success(emptyList())) // Emit empty list for empty query
                return@flow
            }

            val response = remoteDataSource.searchCharacter(name)
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

    override suspend fun getCharacterDetails(id: String): Flow<Resource<CharacterDetailsResponse>> {
        TODO("Not yet implemented")
    }


    override suspend fun getCharacterDetailsByUrl(url: String): Flow<Resource<ComprehensiveDetailModel>> = flow {
        emit(Resource.Loading)

        try {
            // Step 1: Fetch character details
            val response = remoteDataSource.getCharacterDetailsByUrl(url)
            if (!response.isSuccessful || response.body() == null) {
                emit(Resource.Failure("Error fetching character details: ${response.message()}"))
                return@flow
            }
            val characterDetails = response.body()!!

            // Step 2: Perform subsequent API calls in parallel
            val comprehensiveDetailModel = coroutineScope {
                val filmsDeferred = async {
                    characterDetails.films.mapNotNull { filmUrl ->
                        try {
                            remoteDataSource.getFilmsByUrl(filmUrl).takeIf { it.isSuccessful }?.body()
                        } catch (e: Exception) {
                            null // Log or handle specific API call failures
                        }
                    }
                }

                val speciesDeferred = async {
                    characterDetails.species.mapNotNull { speciesUrl ->
                        try {
                            remoteDataSource.getSpeciesByUrl(speciesUrl).takeIf { it.isSuccessful }?.body()
                        } catch (e: Exception) {
                            null // Log or handle specific API call failures
                        }
                    }
                }

                val planetDeferred = async {
                    try {
                        characterDetails.homeworld?.let { homeworldUrl ->
                            remoteDataSource.getPlanetsByUrl(homeworldUrl).takeIf { it.isSuccessful }?.body()
                        }
                    } catch (e: Exception) {
                        null // Log or handle specific API call failures
                    }
                }

                mapper.mapToComprehensiveDetailModel(
                    characterDetails = characterDetails,
                    films = filmsDeferred.await(),
                    species = speciesDeferred.await(),
                    planet = planetDeferred.await()
                )
            }

            emit(Resource.Success(comprehensiveDetailModel))
        } catch (e: Exception) {
            emit(Resource.Failure("Exception occurred: ${e.message}"))
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

}

