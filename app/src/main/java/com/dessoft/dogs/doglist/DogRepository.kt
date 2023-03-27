package com.dessoft.dogs.doglist

import com.dessoft.dogs.Dog
import com.dessoft.dogs.R
import com.dessoft.dogs.api.ApiResponseStatus
import com.dessoft.dogs.api.DogsApi.retrofitService
import com.dessoft.dogs.api.dto.DogDTOMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

class DogRepository {

    suspend fun downloadDogs(): ApiResponseStatus {
        return withContext(
            Dispatchers.IO /*descargar datos u obtener de db */
        ) {
            //getFakeDogs() //dummy, sin consumir el servicio
            try {
                val dogListApiResponse = retrofitService.getAllDogs()
                val dogDTOList = dogListApiResponse.data.dogs
                val dogDTOMapper = DogDTOMapper()
                ApiResponseStatus.Suceess(dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList))
            } catch (e: UnknownHostException) {
                ApiResponseStatus.Error(R.string.unknown_host_exception_error)
            }catch (e:java.lang.Exception){
                ApiResponseStatus.Error(R.string.unknown_error)
            }
        }
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