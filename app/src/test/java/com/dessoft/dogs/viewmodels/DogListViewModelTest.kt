package com.dessoft.dogs.viewmodels

import com.dessoft.dogs.DogListViewModel
import com.dessoft.dogs.api.ApiResponseStatus
import com.dessoft.dogs.doglist.DogTasks
import com.dessoft.dogs.model.Dog
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class DogListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var dogCoroutineRule = DogCoroutineRule()

    /**
     * primero el nombre de lo que va hacer
     * y luego el resultado esperado
     */
    @Test
    fun downloadDogList_StatusCorrect() {
        class FakeDogRepository : DogTasks {
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                return ApiResponseStatus.Success(
                    listOf(
                        Dog(
                            1, 1, "Chihuahua", "Chihuahua", "Toy", "5.4",
                            "6.7", "", "12 - 15", "", "", "10.5",
                            "12.3"
                        ),
                        Dog(
                            2, 1, "Labrador", "Labrador", "Toy", "5.4",
                            "6.7", "", "12 - 15", "", "", "10.5",
                            "12.3"
                        ),
                        Dog(
                            4, 1, "San Bernardo", "San Bernardo", "Toy", "5.4",
                            "6.7", "", "12 - 15", "", "", "10.5",
                            "12.3"
                        ),
                        Dog(
                            5, 1, "Husky", "Husky", "Toy", "5.4",
                            "6.7", "", "12 - 15", "", "", "10.5",
                            "12.3"
                        ),
                        Dog(
                            6, 1, "Xoloscuincle", "Xoloscuincle", "Toy", "5.4",
                            "6.7", "", "12 - 15", "", "", "10.5",
                            "12.3"
                        )
                    )
                )
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                return ApiResponseStatus.Success(Unit)
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
                return ApiResponseStatus.Success(
                    Dog(
                        7, 1, "Other", "Otro", "Toy", "5.4",
                        "6.7", "", "12 - 15", "", "", "10.5",
                        "12.3"
                    )
                )
            }

        }
        //se crea un objeto del viewModel que se va hacer testing
        val viewModel = DogListViewModel(
            dogRepository = FakeDogRepository()
        )

        //El tamaño de la lista es 5?
        assertEquals(5, viewModel.dogList.value.size)
        //El id en la posicion[1], es 2?
        assertEquals(2, viewModel.dogList.value[1].id)
        //El resultado es Success ?
        assert(viewModel.status.value is ApiResponseStatus.Success)
    }

    @Test
    fun downloadDogListError_StatusCorrect() {
        class FakeDogRepository : DogTasks {
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                return ApiResponseStatus.Error(messageId = 401)
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                return ApiResponseStatus.Success(Unit)
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
                return ApiResponseStatus.Success(
                    Dog(
                        7, 1, "Other", "Otro", "Toy", "5.4",
                        "6.7", "", "12 - 15", "", "", "10.5",
                        "12.3"
                    )
                )
            }

        }
        //se crea un objeto del viewModel que se va hacer testing
        val viewModel = DogListViewModel(
            dogRepository = FakeDogRepository()
        )

        //El tamaño de la lista es 0?
        assertEquals(0, viewModel.dogList.value.size)
        //El resultado es Success ?
        assert(viewModel.status.value is ApiResponseStatus.Error)
    }

    @Test
    fun reset_StatusCorrect() {
        class FakeDogRepository : DogTasks {
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                return ApiResponseStatus.Error(messageId = 401)
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                return ApiResponseStatus.Success(Unit)
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
                return ApiResponseStatus.Success(
                    Dog(
                        7, 1, "Other", "Otro", "Toy", "5.4",
                        "6.7", "", "12 - 15", "", "", "10.5",
                        "12.3"
                    )
                )
            }

        }
        //se crea un objeto del viewModel que se va hacer testing
        val viewModel = DogListViewModel(
            dogRepository = FakeDogRepository()
        )

        //El resultado es Success ?
        assert(viewModel.status.value is ApiResponseStatus.Error)

        viewModel.resetApiResponseStatus()


        assert(viewModel.status.value == null)

    }

}