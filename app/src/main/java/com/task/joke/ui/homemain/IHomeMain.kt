package com.task.joke.ui.homemain

import com.task.joke.utils.base.interfaces.IBase

interface IHomeMain {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>
    interface State : IBase.State
}