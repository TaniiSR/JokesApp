package com.task.joke.ui.homemain.jokehome

import android.text.TextWatcher
import androidx.lifecycle.LiveData
import com.task.joke.data.remote.microservices.jokes.responsedtos.JokeResponse
import com.task.joke.ui.homemain.jokehome.adapter.JokeListAdapter
import com.task.joke.utils.base.interfaces.IBase

interface IHome {
    interface View : IBase.View<ViewModel> {
        fun viewModelObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val jokes: LiveData<JokeResponse?>
        val jokeListAdapter: JokeListAdapter
        val watcher: TextWatcher
        val amount: Int
        fun getJokeList(amount: Int)
    }

    interface State : IBase.State
}