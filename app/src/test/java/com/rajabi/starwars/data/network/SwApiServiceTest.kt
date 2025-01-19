package com.rajabi.starwars.data.network

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SwApiServiceTest {
    private lateinit var swAPIServices: SwApiService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        swAPIServices = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SwApiService::class.java)

    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    private fun enqueueMockResponse(
        fileName:String
    ){
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        mockWebServer.enqueue(mockResponse)
    }

    @Test
    public fun getSearch_sentRequest_receivedExpected(){
        runBlocking {
            enqueueMockResponse("products.json")
            val responseBody = swAPIServices.searchCharacters("Luke Skywalker").body()
            val request = mockWebServer.takeRequest()
            assertThat(responseBody?.characters?.get(0)?.name).isNotNull()
            assertThat(request.path).isEqualTo("/people?search=Luke%20Skywalker")
        }
    }
}