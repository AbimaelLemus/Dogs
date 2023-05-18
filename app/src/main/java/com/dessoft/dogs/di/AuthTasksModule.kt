package com.dessoft.dogs.di

import com.dessoft.dogs.auth.AuthRepository
import com.dessoft.dogs.auth.AuthTasks
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthTasksModule {
    @Binds
    abstract fun bindAuthTasks(
        authRepository: AuthRepository
    ): AuthTasks
}