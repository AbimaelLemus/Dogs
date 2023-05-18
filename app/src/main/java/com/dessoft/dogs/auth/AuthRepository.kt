package com.dessoft.dogs.auth

import com.dessoft.dogs.api.ApiResponseStatus
import com.dessoft.dogs.api.ApiService
import com.dessoft.dogs.api.dto.LoginDTO
import com.dessoft.dogs.api.dto.SignUpDTO
import com.dessoft.dogs.api.dto.UserDTOMapper
import com.dessoft.dogs.api.makeNetworkCall
import com.dessoft.dogs.model.User
import javax.inject.Inject


interface AuthTasks {
    suspend fun login(
        email: String,
        password: String
    ): ApiResponseStatus<User>

    suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ): ApiResponseStatus<User>
}

/*
* USER:hackaprende@gmail.com
* PSW: test1234
* */

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
) : AuthTasks {

    override suspend fun login(
        email: String,
        password: String
    ): ApiResponseStatus<User> = makeNetworkCall {
        val loginDTO = LoginDTO(email, password)
        val loginResponse = apiService.login(loginDTO)

        if (!loginResponse.isSuccess) {
            throw Exception(loginResponse.message)
        }

        val userDTO = loginResponse.data.user
        val userDTOMapper = UserDTOMapper()
        userDTOMapper.fromUserDTOToUserDomain(userDTO)
    }

    override suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ): ApiResponseStatus<User> = makeNetworkCall {
        val signUpDTO = SignUpDTO(email, password, passwordConfirmation)
        val signUpResponse = apiService.signUp(signUpDTO)

        if (!signUpResponse.isSuccess) {
            throw Exception(signUpResponse.message)
        }

        val userDTO = signUpResponse.data.user
        val userDTOMapper = UserDTOMapper()
        userDTOMapper.fromUserDTOToUserDomain(userDTO)
    }
}
