package com.dessoft.dogs.api

import okhttp3.Interceptor
import okhttp3.Response

//Este es un singleton, mientras la app este acctiva va a guardar en este caso el token
object ApiServiceInterceptor : Interceptor {

    const val NEEDS_AUTH_HEADER_KEY = "needs_authentication"
    private var sessionToken: String? = null

    fun setSessionToken(sessionToken: String) {
        this.sessionToken = sessionToken
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()

        if (request.header(NEEDS_AUTH_HEADER_KEY) != null) {
            //Needs codentials
            if (sessionToken == null) {
                throw java.lang.RuntimeException("Nedd to be authenticated to perform")
            } else {
                requestBuilder.addHeader("AUTH_TOKEN", sessionToken!!)
            }
        }
        return chain.proceed(requestBuilder.build())
    }
}