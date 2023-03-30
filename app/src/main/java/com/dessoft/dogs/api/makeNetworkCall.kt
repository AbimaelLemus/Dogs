package com.dessoft.dogs.api

import com.dessoft.dogs.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException

const val UNAUTHORIZED_ERROR_CODE = 401
suspend fun <T> makeNetworkCall(
    call: suspend () -> T
): ApiResponseStatus<T> =
    withContext(
        Dispatchers.IO /*descargar datos u obtener de db */
    ) {
        //getFakeDogs() //dummy, sin consumir el servicio
        try {
            ApiResponseStatus.Success(call())
        } catch (e: UnknownHostException) {
            ApiResponseStatus.Error(R.string.unknown_host_exception_error)
        } catch (e: HttpException) {
            val errorMessage = if (e.code() == UNAUTHORIZED_ERROR_CODE) {
                R.string.wrong_user_or_password
            } else {
                R.string.unknown_error
            }
            ApiResponseStatus.Error(errorMessage)
        } catch (e: Exception) {
            val errorMessage = when (e.message) {
                "sign_up_error" -> R.string.sign_up_error
                "sign_in_error" -> R.string.sign_in_error
                "user_already_exists" -> R.string.user_already_exists
                "error_adding_dog" -> R.string.error_adding_dog
                else -> R.string.unknown_error
            }
            ApiResponseStatus.Error(errorMessage)
        }

    }