package com.dessoft.dogs.doglist

import com.dessoft.dogs.R
import com.dessoft.dogs.api.ApiResponseStatus
import com.dessoft.dogs.api.ApiService
import com.dessoft.dogs.api.dto.AddDogToUserDTO
import com.dessoft.dogs.api.dto.DogDTOMapper
import com.dessoft.dogs.api.makeNetworkCall
import com.dessoft.dogs.model.Dog
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject


interface DogTasks {
    //aca solo van los metodos publicos
    suspend fun getDogCollection(): ApiResponseStatus<List<Dog>>
    suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any>
    suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog>

}

/*
* @Binds: para implementacion de interfaces del proyecto
* @Provides: clases de las cuales el proyecto no es dueño
*            por ej: retrofit,
* Postd:aunque tambien se puede usar cualquiera de las dos android recomienda lo de arriba
* */

class DogRepository @Inject constructor(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher,
    //en produccion es un dispatcher IO, porque corresponde a las operaciones que tenemos
    //pero en test va a ser otro dispatcher
) : DogTasks {

    override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
        return withContext(dispatcher) {
            /**como esta en una corroutina, primero se va a llamar allDogs y luego userDogs
            val allDogsListResponse = downloadDogs()
            val userDogsListResponse = getUserDogs()
            por ejemplo si el de all tarda 5s y el user 3s, en total serian 8s, para optimizarlo
            se usan los deferred, lo que hace es que no va esperar los 8s, sino que se van a
            ejecutar al mismo tiempo, y por mucho va a tardar 5s*/
            val allDogsListDeferred = async { downloadDogs() }
            val userDogsListDeferred = async { getUserDogs() }

            val allDogsListResponse = allDogsListDeferred.await()
            val userDogsListResponse = userDogsListDeferred.await()

            when {
                allDogsListResponse is ApiResponseStatus.Error -> {
                    allDogsListResponse
                }
                userDogsListResponse is ApiResponseStatus.Error -> {
                    userDogsListResponse
                }
                allDogsListResponse is ApiResponseStatus.Success
                        && userDogsListResponse is ApiResponseStatus.Success -> {

                    ApiResponseStatus.Success(
                        getCollectionList(
                            allDogsListResponse.data,
                            userDogsListResponse.data
                        )
                    )
                }
                else -> {
                    ApiResponseStatus.Error(R.string.unknown_error)
                }
            }
        }

    }

    private fun getCollectionList(allDogList: List<Dog>, userDogList: List<Dog>): List<Dog> {
        return allDogList.map {
            if (userDogList.contains(it)) {
                it
            } else {
                Dog(
                    0, it.index, "", "", "", "", "",
                    "", "", "", "", "", "", inCollection = false
                )
            }
        }.sorted()
    }

    private suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val dogListApiResponse = apiService.getAllDogs()
        val dogDTOList = dogListApiResponse.data.dogs
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
    }

    private suspend fun getUserDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val dogListApiResponse = apiService.getUserDogs()
        val dogDTOList = dogListApiResponse.data.dogs
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
    }

    override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> = makeNetworkCall {
        val addDogToUserDTO = AddDogToUserDTO(dogId)
        val defaultResponse = apiService.addDogToUser(addDogToUserDTO)

        if (!defaultResponse.isSuccess) {
            throw Exception(defaultResponse.message)
        }

    }

    override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> = makeNetworkCall {
        val response = apiService.getDogByMlId(mlDogId)

        if (!response.isSuccess) {
            throw Exception(response.message)
        }

        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOToDogDomain(response.data.dog)
    }

    private fun getFakeDogs(): MutableList<Dog> {
        val dogList = mutableListOf<Dog>()
        dogList.add(
            Dog(
                1, 1, "Chihuahua", "Chihuahua", "Toy", "5.4",
                "6.7", "", "12 - 15", "", "", "10.5",
                "12.3"
            )
        )
        dogList.add(
            Dog(
                2, 1, "Labrador", "Labrador", "Toy", "5.4",
                "6.7", "", "12 - 15", "", "", "10.5",
                "12.3"
            )
        )
        dogList.add(
            Dog(
                3, 1, "Retriever", "Retriever", "Toy", "5.4",
                "6.7", "", "12 - 15", "", "", "10.5",
                "12.3"
            )
        )
        dogList.add(
            Dog(
                4, 1, "San Bernardo", "San Bernardo", "Toy", "5.4",
                "6.7", "", "12 - 15", "", "", "10.5",
                "12.3"
            )
        )
        dogList.add(
            Dog(
                5, 1, "Husky", "Husky", "Toy", "5.4",
                "6.7", "", "12 - 15", "", "", "10.5",
                "12.3"
            )
        )
        dogList.add(
            Dog(
                6, 1, "Xoloscuincle", "Xoloscuincle", "Toy", "5.4",
                "6.7", "", "12 - 15", "", "", "10.5",
                "12.3"
            )
        )
        return dogList
    }

}