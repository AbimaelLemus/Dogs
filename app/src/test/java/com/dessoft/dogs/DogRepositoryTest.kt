package com.dessoft.dogs

import com.dessoft.dogs.api.ApiResponseStatus
import com.dessoft.dogs.api.ApiService
import com.dessoft.dogs.api.dto.AddDogToUserDTO
import com.dessoft.dogs.api.dto.DogDTO
import com.dessoft.dogs.api.dto.LoginDTO
import com.dessoft.dogs.api.dto.SignUpDTO
import com.dessoft.dogs.api.responses.AuthApiResponse
import com.dessoft.dogs.api.responses.DefaultResponse
import com.dessoft.dogs.api.responses.DogApiResponse
import com.dessoft.dogs.api.responses.DogListApiResponse
import com.dessoft.dogs.api.responses.DogListResponse
import com.dessoft.dogs.api.responses.DogResponse
import com.dessoft.dogs.doglist.DogRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.*
import org.junit.Test
import java.net.UnknownHostException

@OptIn(ExperimentalCoroutinesApi::class)
class DogRepositoryTest {

    @Test
    fun testGetDogCollectionSuccess(): Unit = runBlocking {

        class FakeApiService : ApiService {
            override suspend fun getAllDogs(): DogListApiResponse {
                return DogListApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogListResponse(
                        dogs =
                        listOf(
                            DogDTO(
                                1, 1, "Chihuahua", "Chihuahua", "Toy", 5.4,
                                6.7, "", "12 - 15", "", "", "10.5",
                                "12.3"
                            ),
                            DogDTO(
                                2, 1, "Labrador", "Labrador", "Toy", 5.4,
                                6.7, "", "12 - 15", "", "", "10.5",
                                "12.3"
                            ),
                            DogDTO(
                                4, 1, "San Bernardo", "San Bernardo", "Toy", 5.4,
                                6.7, "", "12 - 15", "", "", "10.5",
                                "12.3"
                            ),
                            DogDTO(
                                5, 1, "Husky", "Husky", "Toy", 5.4,
                                6.7, "", "12 - 15", "", "", "10.5",
                                "12.3"
                            ),
                            DogDTO(
                                6, 1, "Xoloscuincle", "Xoloscuincle", "Toy", 5.4,
                                6.7, "", "12 - 15", "", "", "10.5",
                                "12.3"
                            )
                        )
                    )
                )
            }

            override suspend fun signUp(signUpDTO: SignUpDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun login(loginDTO: LoginDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDogToUserDTO: AddDogToUserDTO): DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                return DogListApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogListResponse(
                        dogs =
                        listOf(
                            DogDTO(
                                6, 1, "Xoloscuincle", "Xoloscuincle", "Toy", 5.4,
                                6.7, "", "12 - 15", "", "", "10.5",
                                "12.3"
                            )
                        )
                    )
                )
            }

            override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                TODO("Not yet implemented")
            }

        }

        /**
         * aca no es necesario el coroutine rule
         * porque en el viewModel el dispatcher no lo podiamos inyectar porque el viewModelScope usa el main
         */
        val dogRepository = DogRepository(
            apiService = FakeApiService(),
            dispatcher = TestCoroutineDispatcher()
        )

        /**
         * en vez de continuar en otro hilo debe de esperar la respuesta, en este caso se le agregoo el runBloking
         */
        val apiResponseStatus = dogRepository.getDogCollection()
        assert(apiResponseStatus is ApiResponseStatus.Success)

        val dogCollection = (apiResponseStatus as ApiResponseStatus.Success).data
        //Se revisa si el tamaño del la respuesta es 5
        assertEquals(5, dogCollection.size)
        //Se revisa que el perro en la pos [0] se aun Xoloscuincle
        assertEquals("Xoloscuincle", dogCollection[0].nameEs)
        //Se revisa que sea falso que el perro en la [0], este vacio
        assertFalse(dogCollection[0].nameEn == "")


    }

    @Test
    fun testGetDogCollectionError(): Unit = runBlocking {
        class FakeApiService : ApiService {
            override suspend fun getAllDogs(): DogListApiResponse {
                throw UnknownHostException()
            }

            override suspend fun signUp(signUpDTO: SignUpDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun login(loginDTO: LoginDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDogToUserDTO: AddDogToUserDTO): DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                return DogListApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogListResponse(
                        dogs =
                        listOf(
                            DogDTO(
                                6, 1, "Xoloscuincle", "Xoloscuincle", "Toy", 5.4,
                                6.7, "", "12 - 15", "", "", "10.5",
                                "12.3"
                            )
                        )
                    )
                )
            }

            override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                TODO("Not yet implemented")
            }

        }

        /**
         * aca no es necesario el coroutine rule
         * porque en el viewModel el dispatcher no lo podiamos inyectar porque el viewModelScope usa el main
         */
        val dogRepository = DogRepository(
            apiService = FakeApiService(),
            dispatcher = TestCoroutineDispatcher()
        )

        /**
         * en vez de continuar en otro hilo debe de esperar la respuesta, en este caso se le agregoo el runBloking
         */
        val apiResponseStatus = dogRepository.getDogCollection()
        assert(apiResponseStatus is ApiResponseStatus.Error)
        assertEquals(
            R.string.unknown_host_exception_error,
            (apiResponseStatus as ApiResponseStatus.Error).messageId
        )
    }

    @Test
    fun getDogByMlSuccess() = runBlocking {
        class FakeApiService : ApiService {
            override suspend fun getAllDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signUp(signUpDTO: SignUpDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun login(loginDTO: LoginDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDogToUserDTO: AddDogToUserDTO): DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                return DogApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogResponse(
                        dog = DogDTO(
                            5, 1, "Husky", "Husky", "Toy", 5.4,
                            6.7, "", "12 - 15", "", "", "10.5",
                            "12.3"
                        ),
                    )
                )
            }

        }

        val dogRepository = DogRepository(
            apiService = FakeApiService(),
            dispatcher = TestCoroutineDispatcher()
        )

        val apiResponseStatus = dogRepository.getDogByMlId("sffsgfds")
        assert(apiResponseStatus is ApiResponseStatus.Success)
        assertEquals(5, (apiResponseStatus as ApiResponseStatus.Success).data.id)
    }

    @Test
    fun getDogByMlError() = runBlocking {
        class FakeApiService : ApiService {
            override suspend fun getAllDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signUp(signUpDTO: SignUpDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun login(loginDTO: LoginDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDogToUserDTO: AddDogToUserDTO): DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                return DogApiResponse(
                    message = "",
                    isSuccess = false,
                    data = DogResponse(
                        dog = DogDTO(
                            5, 1, "Husky", "Husky", "Toy", 5.4,
                            6.7, "", "12 - 15", "", "", "10.5",
                            "12.3"
                        ),
                    )
                )
            }

        }

        val dogRepository = DogRepository(
            apiService = FakeApiService(),
            dispatcher = TestCoroutineDispatcher()
        )

        val apiResponseStatus = dogRepository.getDogByMlId("sffsgfds")
        assert(apiResponseStatus is ApiResponseStatus.Error)
        assertEquals(R.string.unknown_error, (apiResponseStatus as ApiResponseStatus.Error).messageId)
    }


}