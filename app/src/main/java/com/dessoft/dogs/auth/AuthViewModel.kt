package com.dessoft.dogs.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dessoft.dogs.R
import com.dessoft.dogs.api.ApiResponseStatus
import com.dessoft.dogs.model.User
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    var user = mutableStateOf<User?>(null)
        private set

    var emailError = mutableStateOf<Int?>(null)
        private set

    var passwordError = mutableStateOf<Int?>(null)
        private set

    var confirmPasswordError = mutableStateOf<Int?>(null)
        private set

    var status = mutableStateOf<ApiResponseStatus<User>?>(null)
        private set

    private val authRepository = AuthRepository()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            handleResponseStatus(authRepository.login(email, password))
        }
    }

    fun singUp(email: String, password: String, passwordConfirmation: String) {
        when {
            email.isEmpty() -> {
                emailError.value = R.string.email_is_no_valid
            }
            password.isEmpty() -> {
                passwordError.value = R.string.password_must_not_be_empty
            }
            passwordConfirmation.isEmpty() -> {
                confirmPasswordError.value = R.string.password_must_not_be_empty
            }
            password != passwordConfirmation -> {
                passwordError.value = R.string.passwords_do_not_match
                confirmPasswordError.value = R.string.passwords_do_not_match
            }
            else -> {
                viewModelScope.launch {
                    status.value = ApiResponseStatus.Loading()
                    handleResponseStatus(
                        authRepository.signUp(
                            email,
                            password,
                            passwordConfirmation
                        )
                    )
                }
            }
        }
    }

    fun resetErrors() {
        emailError.value = null
        passwordError.value = null
        confirmPasswordError.value = null
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<User>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            user.value = apiResponseStatus.data!!
        }
        status.value = apiResponseStatus
    }

    fun resetApiResponseStatus() {
        status.value = null
    }

}