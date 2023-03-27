package com.dessoft.dogs.auth

import com.dessoft.dogs.api.ApiResponseStatus
import com.dessoft.dogs.api.DogsApi
import com.dessoft.dogs.api.dto.SignUpDTO
import com.dessoft.dogs.api.dto.UserDTOMapper
import com.dessoft.dogs.api.makeNetworkCall
import com.dessoft.dogs.model.User

class AuthRepository {

    suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ): ApiResponseStatus<User> = makeNetworkCall {
        val signUpDTO = SignUpDTO(email, password, passwordConfirmation)
        val signUpResponse = DogsApi.retrofitService.signUp(signUpDTO)

        if (!signUpResponse.isSuccess) {
            throw Exception(signUpResponse.message)
        }

        val userDTO = signUpResponse.data.user
        val userDTOMapper = UserDTOMapper()
        userDTOMapper.fromUserDTOToUserDomain(userDTO)
    }
}
