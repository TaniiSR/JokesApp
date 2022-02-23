package com.task.joke.domain

import android.content.Context
import com.task.joke.data.remote.apiclient.base.ApiResponse
import com.task.joke.data.remote.microservices.jokes.JokeApi
import com.task.joke.data.remote.microservices.jokes.responsedtos.JokeResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DataRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val productApi: JokeApi
) :
    IDataRepository {
    override suspend fun getJokes(amount: Int): JokeResponse? {
        return when (val response = productApi.getJokes(amount)) {
            is ApiResponse.Success -> {
                response.data
            }
            is ApiResponse.Error -> {
                null
            }
        }
    }

}