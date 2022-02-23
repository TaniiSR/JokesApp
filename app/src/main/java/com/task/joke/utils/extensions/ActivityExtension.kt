package com.task.joke.utils.extensions

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewbinding.ViewBinding
import com.task.joke.R
import com.task.joke.utils.base.interfaces.BaseViewHolder

fun Activity.hideSystemUI(rootView: View) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    WindowInsetsControllerCompat(window, rootView).let { controller ->
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

fun Activity.showSystemUI(rootView: View) {
    WindowCompat.setDecorFitsSystemWindows(window, true)
    WindowInsetsControllerCompat(
        window,
        rootView
    ).show(WindowInsetsCompat.Type.systemBars())
}

inline fun <reified T : ViewBinding> Activity.showDialog(
    dialogVH: BaseViewHolder,
    isCancelable: Boolean,
    noinline getViewBinding: (() -> T)

): AlertDialog {
    val mViewBinding = getViewBinding()
    val builder = AlertDialog.Builder(this)
    var alertDialog: AlertDialog? = null
    val marginPx = resources.getDimension(R.dimen.ld_margin_medium).toInt()
    builder.setView(mViewBinding.root)
    builder.setCancelable(isCancelable)
    alertDialog = builder.create()
    dialogVH.onBind(mViewBinding)
    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    alertDialog.window?.attributes?.height = resources.getDimension(R.dimen.ld_margin_xxxl).toInt()
    alertDialog.window?.decorView?.setPadding(marginPx, 0, marginPx, 0)
    alertDialog.show()
    return alertDialog
}