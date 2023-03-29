package com.dessoft.dogs.api

import com.dessoft.dogs.api.dto.SignUpDTO
import com.dessoft.dogs.api.responses.DogListApiResponse
import com.dessoft.dogs.api.responses.SignUpApiResponse
import com.dessoft.dogs.utils.BASE_URL
import com.dessoft.dogs.utils.GET_ALL_DOGS_URL
import com.dessoft.dogs.utils.SIGN_UP
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

    @POST(SIGN_UP)
    suspend fun signUp(@Body signUpDTO: SignUpDTO): SignUpApiResponse
}

object DogsApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
