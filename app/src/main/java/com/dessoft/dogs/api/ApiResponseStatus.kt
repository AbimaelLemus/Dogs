package com.dessoft.dogs.api

import com.dessoft.dogs.Dog

sealed class ApiResponseStatus() {
    class Suceess(val dogList: List<Dog>) : ApiResponseStatus()
    class Loading() : ApiResponseStatus()
    class Error(val messageId: Int) : ApiResponseStatus()
}