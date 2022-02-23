package com.task.joke.di

import com.task.joke.data.remote.microservices.jokes.JokeApi
import com.task.joke.data.remote.microservices.jokes.JokeRepositoryRemote
import com.task.joke.domain.DataRepository
import com.task.joke.domain.IDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun provideDataRepositoryRepository(dataRepository: DataRepository): IDataRepository

    @Binds
    @Singleton
    abstract fun provideJokeRepository(jokeRepositoryRemote: JokeRepositoryRemote): JokeApi
}