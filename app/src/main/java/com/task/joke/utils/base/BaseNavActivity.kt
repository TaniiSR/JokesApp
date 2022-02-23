package com.task.joke.utils.base

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.task.joke.R
import com.task.joke.utils.EXTRA
import com.task.joke.utils.base.interfaces.IBase
import com.task.joke.utils.extensions.addExtras
import com.task.joke.utils.extensions.plus

/**
 * A base BaseNavViewModel Activity with built-in support for Android X Navigation Concept and ViewModel.
 */
abstract class BaseNavActivity<VB : ViewBinding, VS : IBase.State, VM : IBase.ViewModel<VS>> :
    BaseActivity<VB, VS, VM>() {

    /**
     * Used to obtain the exact id of the navigation graph to be used by this activity.
     *
     * @return the id of the navigation graph
     */
    @get:NavigationRes
    abstract val navigationGraphId: Int

    /**
     * Override this property to specify a custom Start Destination.
     *
     * @return the exact id of the destination to be used as the starting one.
     */
    @get:IdRes
    protected open val navigationGraphStartDestination: Int = 0

    /**
     * Accesses the The NavController associated with the current activity.
     */
    val navController: NavController
        get() = findNavController(R.id.nav_host_fragment)

    /**
     * The initial input to be provided to the start destination fragment.
     */
    protected open var startDestinationInput: Bundle? = Bundle()
    protected open var extrasBundle = Bundle()
    private var navHostFragment: NavHostFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent?.hasExtra(EXTRA) == true) {
            startDestinationInput = intent?.getBundleExtra(EXTRA)
        }
        initNavigationGraph()
    }

    private val onDestinationChangedListener =
        NavController.OnDestinationChangedListener { controller, destination, arguments ->
            onDestinationChanged(controller, destination, arguments)
        }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(onDestinationChangedListener)
    }

    override fun onPause() {
        super.onPause()
        navController.removeOnDestinationChangedListener(onDestinationChangedListener)
    }

    abstract fun onDestinationChanged(
        controller: NavController?,
        destination: NavDestination?,
        arguments: Bundle?
    )

    protected fun getCurrentFragment() = navHostFragment?.childFragmentManager?.fragments?.get(0)


    private fun initNavigationGraph() {
        try {
            navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
            navHostFragment?.navController?.apply {
                navInflater
                graph = navInflater.inflate(navigationGraphId).also {
                    it.setStartDestination(
                        (if (navigationGraphStartDestination != 0) navigationGraphStartDestination else it.startDestinationId)
                    )
                    it.addExtras(extrasBundle.plus(startDestinationInput ?: Bundle()))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException(e.message)
        }
    }

    override fun onBackPressed() {
        if ((getCurrentFragment() as BaseFragment<*, *, *>).onBackPressed())
            super.onBackPressed()
    }
}
