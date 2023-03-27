package com.dessoft.dogs.auth

import com.dessoft.dogs.Dog
import com.dessoft.dogs.api.ApiResponseStatus
import com.dessoft.dogs.api.DogsApi
import com.dessoft.dogs.api.dto.DogDTOMapper
import com.dessoft.dogs.api.dto.SignUpDTO

class AuthRepository {

    suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ): ApiResponseStatus<> =  {
        //getFakeDogs() //dummy, sin consumir el servicio

        val signUpDTO = SignUpDTO(email, password, passwordConfirmation)
        val signUpResponse = DogsApi.retrofitService.signUp(signUpDTO)
        val userDTOMapper = signUpResponse.data.user
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
    }
}

}