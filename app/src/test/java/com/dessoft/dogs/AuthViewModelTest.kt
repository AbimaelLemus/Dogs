package com.dessoft.dogs

import com.dessoft.dogs.api.ApiResponseStatus
import com.dessoft.dogs.auth.AuthTasks
import com.dessoft.dogs.auth.AuthViewModel
import com.dessoft.dogs.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class AuthViewModelTest {

    @ExperimentalCoroutinesApi
    @get: Rule
    var dogCoroutineRule = DogCoroutineRule()

    @Test
    fun testLoginValidationCorrect() {
        class FakeAuthRepository : AuthTasks {
            override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(
                    User(1L, "hackaprende@gmail.com", "")
                )
            }

            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirmation: String
            ): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(
                    User(1L, "", "")
                )
            }
        }

        val viewModel = AuthViewModel(
            authRepository = FakeAuthRepository()
        )

        /**Validamos email*/
        viewModel.login("", "test1234")
        assertEquals(R.string.email_is_not_valid, viewModel.emailError.value)

        /**Validamos psw*/
        viewModel.login("hackaprende@gmail.com", "")
        assertEquals(
            R.string.password_must_not_be_empty,
            viewModel.passwordError.value
        )
    }

    @Test
    fun testLoginStateCorrect() {

        val fakeUser = User(1L, "bisman.lemus@gmail.com", "")

        class FakeAuthRepository : AuthTasks {
            override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(
                    fakeUser
                )
            }

            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirmation: String
            ): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(
                    User(1L, "", "")
                )
            }
        }

        val viewModel = AuthViewModel(
            authRepository = FakeAuthRepository()
        )

        /**Validamos que los email son iguales*/
        viewModel.login("bisman.lemus@gmail.com", "test1234")
        assertEquals(fakeUser.email, viewModel.user.value?.email)
    }

}