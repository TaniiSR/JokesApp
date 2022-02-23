

@file:JvmName("NavigationUtils")

package com.task.joke.utils.extensions

import android.os.Bundle
import androidx.navigation.NavArgument
import androidx.navigation.NavController
import androidx.navigation.NavGraph


/**
 * Determines if the current Fragment Page is the start destination
 * (according to the associated navigation graph).
 */
val NavController.isOnStartDestination: Boolean
    get() = (this.currentDestination?.id == this.graph.startDestinationId)


/**
 * Extracts all the arguments present in the specified [Bundle]
 * and adds them to the specified [NavGraph].
 *
 * @param extras a bundle of arguments
 */
internal fun NavGraph.addExtras(extras: Bundle) {
    for (key in extras.keySet()) {
        this.addArgument(key, newNavArgument(extras.get(key)))
    }
}


private fun newNavArgument(value: Any?): NavArgument {
    return NavArgument.Builder()
        .setDefaultValue(value)
        .setIsNullable(value == null)
        .build()
}