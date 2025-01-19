package com.rajabi.starwars.data.network

import com.rajabi.starwars.data.model.search.CharactersResponse
import com.rajabi.starwars.data.model.detail.CharacterDetailsResponse
import com.rajabi.starwars.data.model.detail.Films
import com.rajabi.starwars.data.model.detail.Planets
import com.rajabi.starwars.data.model.detail.Species
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface SwApiService {
    @GET("people")
    suspend fun searchCharacters(@Query("search") name: String): Response<CharactersResponse>

    @GET("people/{id}")
    suspend fun getCharacterDetails(@Path("id") id: String): Response<CharacterDetailsResponse>

    @GET
    suspend fun getCharacterDetailByUrl(@Url url: String): Response<CharacterDetailsResponse>

    @GET
    suspend fun getFilmsDetailByUrl(@Url url: String): Response<Films>

    @GET
    suspend fun getPlanetsDetailByUrl(@Url url: String): Response<Planets>

    @GET
    suspend fun getSpeciesDetailByUrl(@Url url: String): Response<Species>
}