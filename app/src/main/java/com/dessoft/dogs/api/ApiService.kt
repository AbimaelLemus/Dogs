package com.dessoft.dogs.api

import com.dessoft.dogs.api.dto.AddDogToUserDTO
import com.dessoft.dogs.api.dto.LoginDTO
import com.dessoft.dogs.api.dto.SignUpDTO
import com.dessoft.dogs.api.responses.AuthApiResponse
import com.dessoft.dogs.api.responses.DefaultResponse
import com.dessoft.dogs.api.responses.DogListApiResponse
import com.dessoft.dogs.utils.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

//sirve tambien para que los request solo sirvan si el usuario esta logueado
private val okHttpClient = OkHttpClient
    .Builder()
    .addInterceptor(ApiServiceInterceptor)
    .build()

private val retrofit = Retrofit.Builder()
    .client(okHttpClient)
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

    //como no se puede pasar el token directamente y esta en la clase user,
    // y dicha clase ocupa un acticity, para ello se ocupa un interceptor
    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @POST(ADD_DOG_USER_URL)
    suspend fun addDogToUser(@Body addDogToUserDTO: AddDogToUserDTO): DefaultResponse

    //va a traer los perros para el usuario, y ese usuario actual esta en el interceptor
    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @GET(GET_USER_DOGS_URL)
    suspend fun getUserDogs(): DogListApiResponse
}

object DogsApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
