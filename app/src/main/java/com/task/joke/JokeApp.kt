package com.task.joke

import android.app.Application
import com.task.joke.data.remote.apiclient.base.RetroNetwork
import com.task.joke.utils.BASE_URL
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class JokeApp : Application() {
    @Inject
    lateinit var retroNetwork: RetroNetwork
    override fun onCreate() {
        super.onCreate()
        retroNetwork.build(BASE_URL)
    }
}