package com.dessoft.dogs.api

import com.dessoft.dogs.api.dto.LoginDTO
import com.dessoft.dogs.api.dto.SignUpDTO
import com.dessoft.dogs.api.responses.DogListApiResponse
import com.dessoft.dogs.api.responses.AuthApiResponse
import com.dessoft.dogs.utils.BASE_URL
import com.dessoft.dogs.utils.GET_ALL_DOGS_URL
import com.dessoft.dogs.utils.SIGN_IN_URL
import com.dessoft.dogs.utils.SIGN_UP_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

interface ApiService {
    @GET(GET_ALL_DOGS_URL)
    suspend fun getAllDogs(): DogListApiResponse

    @POST(SIGN_UP_URL)
    suspend fun signUp(@Body signUpDTO: SignUpDTO): AuthApiResponse

    @POST(SIGN_IN_URL)
    suspend fun login(@Body loginDTO: LoginDTO): AuthApiResponse
}

object DogsApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
