package com.task.joke.data.remote.microservices.jokes

import com.task.joke.data.remote.apiclient.base.ApiResponse
import com.task.joke.data.remote.apiclient.base.BaseRepository
import com.task.joke.data.remote.microservices.jokes.responsedtos.JokeResponse
import javax.inject.Inject

class JokeRepositoryRemote @Inject constructor(
    private val service: JokeRetroService
) : BaseRepository(), JokeApi {


    companion object {
        const val URL_GET_JOKES = "/joke/Any"
    }

    override suspend fun getJokes(amount: Int): ApiResponse<JokeResponse> {
        return executeSafely(call = {
            service.getJokes(amount)
        })
    }
}