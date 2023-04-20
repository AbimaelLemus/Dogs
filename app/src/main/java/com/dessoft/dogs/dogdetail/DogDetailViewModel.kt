package com.dessoft.dogs.dogdetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dessoft.dogs.R
import com.dessoft.dogs.api.ApiResponseStatus
import com.dessoft.dogs.doglist.DogRepository
import com.dessoft.dogs.model.Dog

class DogDetailViewModel : ViewModel() {

    private val _dogList = MutableLiveData<List<Dog>>()

    //encapsulamiento
    val dogList: LiveData<List<Dog>>
        get() = _dogList

    //lo de estatus sirve para controlar los estados de la app, al igual que la clase ApiResponseStatus
    //private val _status = MutableLiveData<ApiResponseStatus<Any>>()
    // con compose el mutable se usa de la siguiente manera
    var status = mutableStateOf<ApiResponseStatus<Any>?>(null)
        private set
    //encapsulamiento
    //lo de abajo ya no se usa con compose, v68
    /*val status: LiveData<ApiResponseStatus<Any>>
        get() = _status*/

    private val dogRepository = DogRepository()

    fun addDogToUser(dogId: Long) {
        /*viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            handleAddDogToUserResponseStatus(dogRepository.addDogToUser(dogId))
        }*/
        status.value = ApiResponseStatus.Error(messageId = R.string.app_name)
    }

    private fun handleAddDogToUserResponseStatus(apiResponseStatus: ApiResponseStatus<Any>) {
        status.value = apiResponseStatus
    }

    fun resetApiResponseStatus() {
        status.value = null
    }

}