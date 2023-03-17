package com.dessoft.dogs.doglist

import com.dessoft.dogs.Dog
import com.dessoft.dogs.api.DogsApi.retrofitService
import com.dessoft.dogs.api.dto.DogDTOMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogRepository {

    suspend fun downloadDogs(): List<Dog> {
        return withContext(
            Dispatchers.IO /*descargar datos u obtener de db */
        ) {
            //getFakeDogs() //dummy, sin consumir el servicio
            val dogListApiResponse = retrofitService.getAllDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
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