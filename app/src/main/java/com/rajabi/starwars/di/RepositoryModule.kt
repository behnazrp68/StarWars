package com.rajabi.starwars.di

import com.rajabi.starwars.data.repository.StarWarsRepositoryImpl
import com.rajabi.starwars.data.repository.datasource.StarWarsRemoteDataSource
import com.rajabi.starwars.domain.repository.StarWarsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideStarWarsRepository(
        starWarsRemoteDataSource: StarWarsRemoteDataSource
    ) : StarWarsRepository{
        return StarWarsRepositoryImpl(
            starWarsRemoteDataSource
        )
    }
}