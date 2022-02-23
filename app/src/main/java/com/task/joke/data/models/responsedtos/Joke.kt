package com.task.joke.data.models.responsedtos


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Joke(
    @SerializedName("category")
    val category: String? = null,
    @SerializedName("delivery")
    val delivery: String? = null,
    @SerializedName("flags")
    val flags: Flags? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("lang")
    val lang: String? = null,
    @SerializedName("safe")
    val safe: Boolean? = null,
    @SerializedName("setup")
    val setup: String? = null,
    @SerializedName("joke")
    val joke: String? = null,
    @SerializedName("type")
    val type: String? = null
) : Parcelable