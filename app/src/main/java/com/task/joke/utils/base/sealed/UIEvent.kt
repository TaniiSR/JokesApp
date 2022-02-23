package com.task.joke.utils.base.sealed

sealed class UIEvent {
    data class Loading(val isLoading: Boolean) : UIEvent()
    data class Message(val message: String) : UIEvent()
    data class Alert(val message: String) : UIEvent()
    data class Error(val message: String) : UIEvent()
}