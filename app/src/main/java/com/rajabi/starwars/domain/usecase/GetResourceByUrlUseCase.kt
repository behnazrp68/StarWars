package com.rajabi.starwars.domain.usecase

import com.rajabi.starwars.data.model.detail.CharacterDetailsResponse
import com.rajabi.starwars.domain.repository.StarWarsRepository
import com.rajabi.starwars.util.Resource
import kotlinx.coroutines.flow.Flow

class GetResourceByUrlUseCase(
    private val repository: StarWarsRepository
) {
    suspend fun execute(url:String): Flow<Resource<CharacterDetailsResponse>> {
        return repository.getResourceByUrl(url)
    }
}