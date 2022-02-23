package com.task.joke.utils.base

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.joke.utils.base.interfaces.IBase
import com.task.joke.utils.base.sealed.Dispatcher
import com.task.joke.utils.base.sealed.UIEvent
import kotlinx.coroutines.*


abstract class BaseViewModel<S : IBase.State> : ViewModel(), IBase.ViewModel<S> {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    fun launch(dispatcher: Dispatcher = Dispatcher.Background, block: suspend () -> Unit): Job {
        return viewModelScope.launch(
            when (dispatcher) {
                Dispatcher.Main -> Dispatchers.Main
                Dispatcher.Background -> Dispatchers.IO
                Dispatcher.LongOperation -> Dispatchers.Default
            }
        ) { block() }
    }

    fun <T> launchAsync(block: suspend () -> T): Deferred<T> =
        viewModelScope.async(Dispatchers.IO) {
            block()
        }

    fun onClick(view: View) {
        clickEvent.setValue(view.id)
    }

    override fun hideLoading(onBackGround: Boolean) {
        if (onBackGround)
            viewState.uiEvent.postValue(UIEvent.Loading(false))
        else
            viewState.uiEvent.value = UIEvent.Loading(false)
    }


    override fun showLoading(onBackGround: Boolean) {
        if (onBackGround)
            viewState.uiEvent.postValue(UIEvent.Loading(true))
        else
            viewState.uiEvent.value = UIEvent.Loading(true)
    }

    override fun showToast(message: String) {
        viewState.uiEvent.value = UIEvent.Message(message)
    }

    override fun showToast(message: String, onBackGround: Boolean) {
        if (onBackGround)
            viewState.uiEvent.postValue(UIEvent.Message(message))
        else
            showToast(message)
    }


}