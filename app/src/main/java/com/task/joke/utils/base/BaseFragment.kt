package com.task.joke.utils.base

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.task.joke.utils.base.interfaces.IBase
import com.task.joke.utils.base.interfaces.OnBackPressedListener
import com.task.joke.utils.base.sealed.Dispatcher
import com.task.joke.utils.base.sealed.UIEvent
import com.task.joke.utils.extensions.observe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseFragment<VB : ViewBinding, VS : IBase.State, VM : IBase.ViewModel<VS>>(@LayoutRes val contentLayoutId: Int) :
    IBase.View<VM>, Fragment(contentLayoutId),
    OnBackPressedListener, View.OnClickListener {

    protected val activityLauncher: BetterActivityResult<Intent, ActivityResult> =
        BetterActivityResult.registerActivityForResult(this)

    private lateinit var onBackPressedCallback: OnBackPressedCallback
    lateinit var mViewBinding: VB
    private var progress: Dialog? = null
    abstract fun onClick(id: Int)
    abstract fun getViewBinding(): VB

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewBinding = getViewBinding()
        return mViewBinding.root
    }

    override fun onClick(view: View) {
        viewModel.onClick(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                    showLoading(it.isLoading)
                }
                else -> Unit
            }
        }
    }

    protected fun showToast(msg: String) {
        if (activity is BaseActivity<*, *, *>)
            (activity as BaseActivity<*, *, *>).showToast(msg)
    }

    protected fun showLoading(isVisible: Boolean) {
        if (activity is BaseActivity<*, *, *>)
            (activity as BaseActivity<*, *, *>).showLoader(isVisible)
    }

    fun launch(dispatcher: Dispatcher = Dispatcher.Main, block: suspend () -> Unit) {
        lifecycleScope.launch(
            when (dispatcher) {
                Dispatcher.Main -> Dispatchers.Main
                Dispatcher.Background -> Dispatchers.IO
                Dispatcher.LongOperation -> Dispatchers.Default
            }
        ) { block() }
    }

    fun setBackButtonDispatcher() {
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onBackPressed(): Boolean = true


    override fun onDestroyView() {
        progress?.dismiss()
        viewModel.clickEvent.removeObservers(this)
        viewModel.viewState.uiEvent.removeObservers(this)
        super.onDestroyView()
    }
}
