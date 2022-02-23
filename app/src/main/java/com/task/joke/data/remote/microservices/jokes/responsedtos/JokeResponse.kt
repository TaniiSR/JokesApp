package com.task.joke.data.remote.microservices.jokes.responsedtos


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.task.joke.data.models.responsedtos.Joke
import com.task.joke.data.remote.apiclient.base.BaseApiResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class JokeResponse(
    @SerializedName("error")
    val error: Boolean? = null,
    @SerializedName("amount")
    val amount: Int? = null,
    @SerializedName("jokes")
    val jokes: List<Joke>? = null
) : BaseApiResponse(), Parcelable