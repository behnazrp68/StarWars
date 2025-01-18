package com.rajabi.starwars.domain.usecase

import com.rajabi.starwars.data.model.Character
import com.rajabi.starwars.domain.repository.StarWarsRepository
import com.rajabi.starwars.util.Resource
import kotlinx.coroutines.flow.Flow

class SearchCharacterUseCase(private val repository: StarWarsRepository) {
    suspend fun execute(name: String): Flow<Resource<List<Character>>> {
        return repository.searchCharacter(name)
    }
}