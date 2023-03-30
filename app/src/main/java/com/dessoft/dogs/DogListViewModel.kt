package com.dessoft.dogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dessoft.dogs.api.ApiResponseStatus
import com.dessoft.dogs.doglist.DogRepository
import com.dessoft.dogs.model.Dog
import kotlinx.coroutines.launch

class DogListViewModel : ViewModel() {

    private val _dogList = MutableLiveData<List<Dog>>()

    //encapsulamiento
    val dogList: LiveData<List<Dog>>
        get() = _dogList

    //lo de estatus sirve para controlar los estados de la app, al igual que la clase ApiResponseStatus
    private val _status = MutableLiveData<ApiResponseStatus<Any>>()

    //encapsulamiento
    val status: LiveData<ApiResponseStatus<Any>>
        get() = _status

    private val dogRepository = DogRepository()

    init {
        getDogCollection()
    }

    fun addDogToUser(dogId: Long) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleAddDogToUserResponseStatus(dogRepository.addDogToUser(dogId))
        }
    }

    private fun getDogCollection() {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleResponseStatus(dogRepository.getDogCollection())
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<List<Dog>>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            _dogList.value = apiResponseStatus.data!!
        }
        _status.value = apiResponseStatus as ApiResponseStatus<Any>
    }

    private fun handleAddDogToUserResponseStatus(apiResponseStatus: ApiResponseStatus<Any>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            getDogCollection()
        }
        _status.value = apiResponseStatus
    }

}