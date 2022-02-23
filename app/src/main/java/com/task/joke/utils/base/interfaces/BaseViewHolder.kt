package com.task.joke.utils.base.interfaces

import androidx.viewbinding.ViewBinding

interface BaseViewHolder {
    fun onBind(viewBinding: ViewBinding?)
    fun notifyDatasetRefresh(data: Any)
}