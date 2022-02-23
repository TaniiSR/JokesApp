package com.task.joke.utils.base

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.task.joke.R
import com.task.joke.utils.base.interfaces.IBase
import com.task.joke.utils.base.sealed.UIEvent
import com.task.joke.utils.extensions.observe
import com.task.joke.utils.extensions.toast


abstract class BaseActivity<VB : ViewBinding, VS : IBase.State, VM : IBase.ViewModel<VS>> :
    IBase.View<VM>,
    AppCompatActivity() {

    protected val activityLauncher: BetterActivityResult<Intent, ActivityResult> =
        BetterActivityResult.registerActivityForResult(this)

    lateinit var mViewBinding: VB
    private var progress: Dialog? = null


    abstract fun getViewBinding(): VB

    open fun onClick(id: Int) = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progress = createProgressDialog(this)
        mViewBinding = getViewBinding()
        setContentView(mViewBinding.root)
        setObservers()
    }

    private fun setObservers() {
        observe(viewModel.viewState.uiEvent, ::handleUIEvent)
        observe(viewModel.clickEvent, ::handleClickEvent)
    }

    private fun handleClickEvent(clickEventId: Int) {
        onClick(clickEventId)
    }

    private fun handleUIEvent(uiEvent: UIEvent?) {
        uiEvent?.let {
            when (it) {
                is UIEvent.Message -> {
                    showToast(it.message)
                }
                is UIEvent.Loading -> {
                    showLoader(it.isLoading)
                }
                else -> Unit
            }
        }
    }

    private fun createProgressDialog(context: Context): Dialog {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.progress_dialog)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }

    fun showToast(msg: String) {
        if (msg.isNotBlank()) {
            toast(msg)
        }
    }

    fun showLoader(isVisible: Boolean) {
        if (isVisible) progress?.show() else progress?.dismiss()
    }


    override fun onDestroy() {
        progress?.dismiss()
        viewModel.viewState.uiEvent.removeObservers(this)
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

}