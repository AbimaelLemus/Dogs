package com.dessoft.dogs.api

import com.dessoft.dogs.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

suspend fun <T> makeNetworkCall(
    call: suspend () -> T
): ApiResponseStatus<T> {
    return withContext(
        Dispatchers.IO /*descargar datos u obtener de db */
    ) {
        //getFakeDogs() //dummy, sin consumir el servicio
        try {
            ApiResponseStatus.Suceess(call())
        } catch (e: UnknownHostException) {
            ApiResponseStatus.Error(R.string.unknown_host_exception_error)
        } catch (e: Exception) {
            ApiResponseStatus.Error(R.string.unknown_error)
        }
    }
}