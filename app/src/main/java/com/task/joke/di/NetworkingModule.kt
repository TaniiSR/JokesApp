package com.task.joke.di

import android.content.Context
import com.task.joke.data.remote.apiclient.base.RetroNetwork
import com.task.joke.data.remote.microservices.jokes.JokeRetroService
import com.task.joke.utils.SharedPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkingModule {

    @Singleton
    @Provides
    fun provideProductService(retroNetwork: RetroNetwork): JokeRetroService {
        return retroNetwork.createService(JokeRetroService::class.java)
    }

    @Singleton
    @Provides
    fun provideSharedPreferenceManager(@ApplicationContext context: Context) =
        SharedPreferenceManager(context)
}