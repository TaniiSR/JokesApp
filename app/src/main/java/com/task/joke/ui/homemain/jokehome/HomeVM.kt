package com.task.joke.ui.homemain.jokehome

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.task.joke.data.remote.microservices.jokes.responsedtos.JokeResponse
import com.task.joke.domain.IDataRepository
import com.task.joke.ui.homemain.jokehome.adapter.JokeListAdapter
import com.task.joke.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    override val viewState: HomeState,
    private val dataRepository: IDataRepository
) : BaseViewModel<IHome.State>(), IHome.ViewModel {

    private val _jokes = MutableLiveData<JokeResponse?>()
    override val jokes: LiveData<JokeResponse?> = _jokes

    override val jokeListAdapter: JokeListAdapter = JokeListAdapter(mutableListOf())

    override val amount: Int
        get() = 10

    override fun getJokeList(amount: Int) {
        launch {
            showLoading(onBackGround = true)
            val response = dataRepository.getJokes(amount)
            withContext(Dispatchers.Main) {
                when (response) {
                    null -> {
                        _jokes.value = null
                        hideLoading()
                        showToast("Sorry Something went wrong")
                    }
                    else -> {
                        hideLoading()
                        // Show error from response
                        _jokes.value = response
                    }
                }
            }
        }
    }

    override val watcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        override fun afterTextChanged(s: Editable?) {
            jokeListAdapter.filter.filter(s)
        }
    }

}