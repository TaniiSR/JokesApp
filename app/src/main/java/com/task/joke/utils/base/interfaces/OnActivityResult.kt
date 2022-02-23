package com.task.joke.utils.base.interfaces

interface OnActivityResult<Result> {
    /**
     * Called after receiving a result from the target activity
     */
    fun onActivityResult(result: Result);
}
