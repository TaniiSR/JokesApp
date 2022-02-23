package com.task.joke.utils.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.showSoftKeyboard() {
    if (this.requestFocus()) {
        val imm: InputMethodManager? =
            this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.requestKeyboard(request: Boolean, forced: Boolean) {
    this.requestFocus()
    if (forced) {
        (this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
            InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    } else if (request) {
        (this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
            InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }
}