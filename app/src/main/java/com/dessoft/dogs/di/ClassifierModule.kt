package com.dessoft.dogs.di

import com.dessoft.dogs.machinelearning.ClassifierRepository
import com.dessoft.dogs.machinelearning.ClassifierTasks
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ClassifierModule {
    @Binds
    abstract fun bindClassifierTasks(
        classifierRepository: ClassifierRepository
    ): ClassifierTasks
}