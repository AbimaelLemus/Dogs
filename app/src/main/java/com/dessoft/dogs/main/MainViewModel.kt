package com.dessoft.dogs.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dessoft.dogs.api.ApiResponseStatus
import com.dessoft.dogs.doglist.DogRepository
import com.dessoft.dogs.model.Dog
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    //el _dog se puede manipular solo desde aca (viewmodel)
    private val _dog = MutableLiveData<Dog>()

    //el dog se puede manipular solo desde el main
    //encapsulamiento
    val dog: LiveData<Dog>
        get() = _dog

    //lo de estatus sirve para controlar los estados de la app, al igual que la clase ApiResponseStatus
    private val _status = MutableLiveData<ApiResponseStatus<Dog>>()

    //encapsulamiento
    val status: LiveData<ApiResponseStatus<Dog>>
        get() = _status

    private val dogRepository = DogRepository()

    fun getDogByMlId(mlDogId: String) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleResponseStatus(dogRepository.getDogByMlId(mlDogId))

        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<Dog>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            _dog.value = apiResponseStatus.data!!
        }
        _status.value = apiResponseStatus
    }

}