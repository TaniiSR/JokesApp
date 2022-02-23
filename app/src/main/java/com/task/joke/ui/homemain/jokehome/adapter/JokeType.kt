package com.task.joke.ui.homemain.jokehome.adapter

sealed class JokeType(val type: String) {
    object SINGLE : JokeType("single")
    object TWOPART : JokeType("twopart")
}
