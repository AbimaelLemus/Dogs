package com.dessoft.dogs.api.dto

import com.dessoft.dogs.model.Dog

class DogDTOMapper {

    fun fromDogDTOToDogDomain(dogDTO: DogDTO): Dog {
        return Dog(
            dogDTO.id,
            dogDTO.index,
            dogDTO.nameEn,
            dogDTO.nameEs,
            dogDTO.type,
            dogDTO.heightFemale.toString(),
            dogDTO.heightMale.toString(),
            dogDTO.imageUrl,
            dogDTO.lifeExpectancy,
            dogDTO.temperament,
            dogDTO.temperamentEn,
            dogDTO.weightFemale,
            dogDTO.weightMale
        )
    }

    //transformaciones de todos los elementos
    fun fromDogDTOListToDogDomainList(dogDTOList: List<DogDTO>): List<Dog> {
        return dogDTOList.map { fromDogDTOToDogDomain(it) }
    }

}