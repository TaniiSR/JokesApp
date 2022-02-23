package com.task.joke.data.remote.microservices.jokes

import com.task.joke.data.remote.apiclient.base.ApiResponse
import com.task.joke.data.remote.microservices.jokes.responsedtos.JokeResponse

interface JokeApi {
    suspend fun getJokes(amount: Int): ApiResponse<JokeResponse>
}