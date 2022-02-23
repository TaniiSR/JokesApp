package com.task.joke.data.remote.microservices.jokes

import com.task.joke.data.remote.microservices.jokes.responsedtos.JokeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JokeRetroService {
    //Get the products
    @GET(JokeRepositoryRemote.URL_GET_JOKES)
    suspend fun getJokes(
        @Query("amount") amount: Int?
    ): Response<JokeResponse>
}