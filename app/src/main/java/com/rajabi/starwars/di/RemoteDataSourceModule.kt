package com.rajabi.starwars.di

import com.rajabi.starwars.data.network.SwApiService
import com.rajabi.starwars.data.repository.datasource.StarWarsRemoteDataSource
import com.rajabi.starwars.data.repository.datasourceimple.StarWarsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule {
    @Singleton
    @Provides
    fun provideProductsRemoteDataSource(apiService: SwApiService): StarWarsRemoteDataSource {
        return StarWarsRemoteDataSourceImpl(apiService)
    }
}