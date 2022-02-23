package com.task.joke.utils.base

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Fade
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.viewbinding.ViewBinding
import com.task.joke.R
import com.task.joke.utils.ARGUMENT_NAVIGATION_REQUEST_CODE
import com.task.joke.utils.REQUEST_CODE_NOT_SET
import com.task.joke.utils.base.interfaces.IBase

abstract class BaseNavFragment<VB : ViewBinding, VS : IBase.State, VM : IBase.ViewModel<VS>>(@LayoutRes val layoutId: Int) :
    BaseFragment<VB, VS, VM>(layoutId) {

    private val requestCode: Int
        get() = arguments?.getInt(ARGUMENT_NAVIGATION_REQUEST_CODE, REQUEST_CODE_NOT_SET)
            ?: REQUEST_CODE_NOT_SET

    /**
     * Navigates to the specified destination screen.
     *
     * @param destinationId the id of the destination screen (either the new Activity or Fragment)
     * @param extras the extra arguments to be passed to the destination screen
     * @param navigationExtras
     */
    fun navigate(
        @IdRes destinationId: Int,
        extras: Bundle? = Bundle(),
        navigationExtras: Navigator.Extras? = null,
        navOptions: NavOptions? = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
    ) {
        findNavController().navigate(
            destinationId,
            extras,
            navOptions,
            navigationExtras,
        )
    }

    protected fun initEnterTransitions() {
        sharedElementEnterTransition = ChangeBounds()
        enterTransition = Fade()
    }

    /**
     * Navigates back (pops the back stack) to the previous [MvvmFragment] on the stack.
     */
    protected fun navigateBack() {
        findNavController().popBackStack()
    }

}