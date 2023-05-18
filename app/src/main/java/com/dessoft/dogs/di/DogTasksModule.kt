package com.dessoft.dogs.di

import com.dessoft.dogs.doglist.DogRepository
import com.dessoft.dogs.doglist.DogTasks
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class) //visto desde toda la app y vivo mientras este abierta la app
/**
 * Tambien existen la otras dos opciones
 * 1. ActivityComponent: para activities
 * 2. ViewModelComponent: para view models
 * */
abstract class DogTasksModule {

    @Binds //Encargado de unir la interfaz y la implementacion una vez que se inyecte la interfaz en un constructor
    abstract fun bindDogTasks(
        dogRepository: DogRepository
    ): DogTasks


}