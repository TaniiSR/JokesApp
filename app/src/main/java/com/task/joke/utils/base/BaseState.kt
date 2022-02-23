package com.task.joke.utils.base


import androidx.lifecycle.MutableLiveData
import com.task.joke.utils.base.interfaces.IBase
import com.task.joke.utils.base.sealed.UIEvent

abstract class BaseState : IBase.State {
    override var uiEvent: MutableLiveData<UIEvent> = MutableLiveData()
}