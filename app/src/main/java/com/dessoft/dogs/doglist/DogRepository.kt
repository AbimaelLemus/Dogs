package com.dessoft.dogs.doglist

import com.dessoft.dogs.model.Dog
import com.dessoft.dogs.api.ApiResponseStatus
import com.dessoft.dogs.api.DogsApi.retrofitService
import com.dessoft.dogs.api.dto.DogDTOMapper
import com.dessoft.dogs.api.makeNetworkCall

class DogRepository {

    suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val dogListApiResponse = retrofitService.getAllDogs()
        val dogDTOList = dogListApiResponse.data.dogs
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
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