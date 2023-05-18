package com.dessoft.dogs.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DistapchersModule {

    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

}