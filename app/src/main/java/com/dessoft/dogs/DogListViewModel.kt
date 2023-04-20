package com.dessoft.dogs

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dessoft.dogs.api.ApiResponseStatus
import com.dessoft.dogs.doglist.DogRepository
import com.dessoft.dogs.model.Dog
import kotlinx.coroutines.launch

class DogListViewModel : ViewModel() {

    var dogList = mutableStateOf<List<Dog>>(listOf())
        private set

    //encapsulamiento, se quita en el v71
    /*val dogList: LiveData<List<Dog>>
        get() = _dogList*/

    //lo de estatus sirve para controlar los estados de la app, al igual que la clase ApiResponseStatus
    var status = mutableStateOf<ApiResponseStatus<Any>?>(null)
        private set

    //encapsulamiento, se quita en el v71
    /*val status: LiveData<ApiResponseStatus<Any>>
        get() = _status*/

    private val dogRepository = DogRepository()

    init {
        getDogCollection()
    }

    private fun getDogCollection() {
        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            handleResponseStatus(dogRepository.getDogCollection())
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<List<Dog>>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            dogList.value = apiResponseStatus.data!!
        }
        status.value = apiResponseStatus as ApiResponseStatus<Any>
    }

    fun resetApiResponseStatus() {
        status.value = null
    }

}