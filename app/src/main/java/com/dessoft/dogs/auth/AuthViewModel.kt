package com.dessoft.dogs.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dessoft.dogs.api.ApiResponseStatus
import com.dessoft.dogs.model.User
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val _user = MutableLiveData<User>()

    //encapsulamiento, el livedata no se puede editar
    val user: LiveData<User>
        get() = _user

    //lo de estatus sirve para controlar los estados de la app, al igual que la clase ApiResponseStatus
    private val _status = MutableLiveData<ApiResponseStatus<User>>()

    //encapsulamiento
    val status: LiveData<ApiResponseStatus<User>>
        get() = _status

    private val authRepository = AuthRepository()

    fun singUp(email: String, password: String, passwordConfirmation: String) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleResponseStatus(authRepository.signUp(email, password, passwordConfirmation))
        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<User>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            _user.value = apiResponseStatus.data!!
        }
        _status.value = apiResponseStatus
    }

}