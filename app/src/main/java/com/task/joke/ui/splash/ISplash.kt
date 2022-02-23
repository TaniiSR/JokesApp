package com.task.joke.ui.splash

import com.task.joke.utils.base.interfaces.IBase

interface ISplash {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>
    interface State : IBase.State
}