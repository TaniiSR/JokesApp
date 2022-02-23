package com.task.joke.domain

import com.task.joke.data.remote.microservices.jokes.responsedtos.JokeResponse

interface IDataRepository {
    suspend fun getJokes(amount: Int): JokeResponse?
}