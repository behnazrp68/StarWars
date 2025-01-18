package com.rajabi.starwars.di

import com.rajabi.starwars.domain.repository.StarWarsRepository
import com.rajabi.starwars.domain.usecase.CharacterDetailUseCase
import com.rajabi.starwars.domain.usecase.GetResourceByUrlUseCase
import com.rajabi.starwars.domain.usecase.SearchCharacterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideSearchUseCase(
        starWarsRepository: StarWarsRepository
    ): SearchCharacterUseCase {
        return SearchCharacterUseCase(starWarsRepository)
    }

    @Singleton
    @Provides
    fun provideCharacterDetailUseCase(starWarsRepository: StarWarsRepository): CharacterDetailUseCase =
        CharacterDetailUseCase(starWarsRepository)

    @Singleton
    @Provides
    fun provideGetResourceByUrlUseCase(starWarsRepository: StarWarsRepository): GetResourceByUrlUseCase =
        GetResourceByUrlUseCase(starWarsRepository)
}