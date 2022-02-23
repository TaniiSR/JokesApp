package com.task.joke.data.models.responsedtos


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Flags(
    @SerializedName("explicit")
    val explicit: Boolean?,
    @SerializedName("nsfw")
    val nsfw: Boolean?,
    @SerializedName("political")
    val political: Boolean?,
    @SerializedName("racist")
    val racist: Boolean?,
    @SerializedName("religious")
    val religious: Boolean?,
    @SerializedName("sexist")
    val sexist: Boolean?
) : Parcelable