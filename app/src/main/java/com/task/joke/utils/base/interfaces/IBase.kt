package com.task.joke.utils.base.interfaces

import androidx.lifecycle.MutableLiveData
import com.task.joke.utils.base.SingleClickEvent
import com.task.joke.utils.base.sealed.UIEvent

interface IBase {
    interface View<V : ViewModel<*>> {
        val viewModel: V
    }

    interface ViewModel<S : State> {
        val viewState: S
        val clickEvent: SingleClickEvent
        fun showLoading(onBackGround: Boolean = false)
        fun hideLoading(onBackGround: Boolean = false)
        fun showToast(message: String, onBackGround: Boolean)
        fun showToast(message: String)
    }

    interface State {
        var uiEvent: MutableLiveData<UIEvent>
    }
}