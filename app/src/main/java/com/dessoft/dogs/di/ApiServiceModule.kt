package com.dessoft.dogs.di

import com.dessoft.dogs.api.ApiService
import com.dessoft.dogs.api.ApiServiceInterceptor
import com.dessoft.dogs.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class ApiServiceModule {
    @Provides // en este momento hace falta proveer retrofit, por lo que se creara la funcion provideRetrofit
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides // hace falta proveer el okHttpClient, por lo que se creara la funcion provideHttpClient
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ) = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    fun provideHttpClient() = OkHttpClient
        .Builder()
        .addInterceptor(ApiServiceInterceptor)
        .build()

}