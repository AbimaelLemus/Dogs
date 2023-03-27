package com.dessoft.dogs.api

import com.dessoft.dogs.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

suspend fun <T> makeNetworkCall(
    call: suspend () -> T
): ApiResponseStatus<T> =
    withContext(
        Dispatchers.IO /*descargar datos u obtener de db */
    ) {
        //getFakeDogs() //dummy, sin consumir el servicio
        try {
            ApiResponseStatus.Suceess(call())
        } catch (e: UnknownHostException) {
            ApiResponseStatus.Error(R.string.unknown_host_exception_error)
        } catch (e: Exception) {
            val errorMessage = when (e.message) {
                "sign_up_error" -> R.string.app_name
                "sign_in_error" -> R.string.app_name
                "user_already_exists" -> R.string.app_name
                else -> R.string.unknown_error
            }
            ApiResponseStatus.Error(errorMessage)
        }

    }