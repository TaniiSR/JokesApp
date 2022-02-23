package com.task.joke.utils.uikit.searchview

import android.animation.Animator
import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.FrameLayout
import com.task.joke.databinding.LayoutSearchViewBinding
import com.task.joke.utils.extensions.hideKeyboard
import com.task.joke.utils.extensions.showSoftKeyboard

class SearchView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs),
    View.OnClickListener {
    var mViewBinding: LayoutSearchViewBinding? = null

    init {
        val inflater = LayoutInflater.from(context)
        mViewBinding = LayoutSearchViewBinding.inflate(inflater, this, true)
        setClickListenerOnView()
    }

    private var listener: OnSearchViewClickListener? = null

    fun openSearch() {
        mViewBinding?.etSearch?.setText("")
        mViewBinding?.clSearchOpenView?.visibility = View.VISIBLE
        val circularReveal = ViewAnimationUtils.createCircularReveal(
            mViewBinding?.clSearchOpenView,
            (mViewBinding?.root?.right ?: 0 + (mViewBinding?.root?.left ?: 0)) / 2,
            (mViewBinding?.root?.top ?: 0 + (mViewBinding?.root?.bottom ?: 0)) / 2,
            0f, width.toFloat()
        )
        circularReveal.duration = 300
        mViewBinding?.etSearch?.showSoftKeyboard()
        circularReveal.start()
    }

    fun closeSearch() {
        val circularConceal = ViewAnimationUtils.createCircularReveal(
            mViewBinding?.clSearchOpenView,
            (mViewBinding?.root?.right ?: 0 + (mViewBinding?.root?.left ?: 0)) / 2,
            (mViewBinding?.root?.top ?: 0 + (mViewBinding?.root?.bottom ?: 0)) / 2,
            width.toFloat(), 0f
        )

        circularConceal.duration = 300
        circularConceal.start()
        circularConceal.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) = Unit
            override fun onAnimationCancel(animation: Animator?) = Unit
            override fun onAnimationStart(animation: Animator?) = Unit
            override fun onAnimationEnd(animation: Animator?) {
                mViewBinding?.clSearchOpenView?.visibility = View.INVISIBLE
                mViewBinding?.etSearch?.setText("")
                mViewBinding?.etSearch?.hideKeyboard()
                circularConceal.removeAllListeners()
            }
        })
    }

    private fun setClickListenerOnView() {
        mViewBinding?.vExecuteButton?.setOnClickListener(this)
        mViewBinding?.tvCancel?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            mViewBinding?.vExecuteButton -> {
                listener?.onSearchClicked()
            }
            mViewBinding?.tvCancel -> {
                listener?.onCancelClicked()
            }
        }
    }

    var textWatcher: TextWatcher? = null
        set(value) {
            field = value
            mViewBinding?.etSearch?.addTextChangedListener(textWatcher)
            invalidate()
        }

    fun setOnToolBarViewClickListener(listener: OnSearchViewClickListener) {
        this.listener = listener
    }

    interface OnSearchViewClickListener {
        fun onCancelClicked() {
            // to be handle later
        }

        fun onSearchClicked() {
            // to be handle later
        }
    }
}