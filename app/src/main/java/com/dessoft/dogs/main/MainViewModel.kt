package com.dessoft.dogs.main

import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dessoft.dogs.api.ApiResponseStatus
import com.dessoft.dogs.doglist.DogTasks
import com.dessoft.dogs.machinelearning.ClassifierTasks
import com.dessoft.dogs.machinelearning.DogRecognition
import com.dessoft.dogs.model.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dogRepository: DogTasks,
    private val classifierRepository: ClassifierTasks,
) : ViewModel() {
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

    private val _dogRecognition = MutableLiveData<DogRecognition>()
    val dogRecognition: LiveData<DogRecognition>
        get() = _dogRecognition

    fun recognizeImage(imageProxy: ImageProxy) {
        viewModelScope.launch {
            _dogRecognition.value = classifierRepository.recognizedImage(imageProxy)
        }
    }

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