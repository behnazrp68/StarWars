package com.rajabi.starwars.data.network

import com.rajabi.starwars.data.model.search.CharactersResponse
import com.rajabi.starwars.data.model.detail.CharacterDetailsResponse
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
    suspend fun getResourceByUrl(@Url url: String): Response<CharacterDetailsResponse> //
}