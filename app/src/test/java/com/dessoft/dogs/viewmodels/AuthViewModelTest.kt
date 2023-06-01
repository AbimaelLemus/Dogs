package com.dessoft.dogs.viewmodels

import com.dessoft.dogs.R
import com.dessoft.dogs.api.ApiResponseStatus
import com.dessoft.dogs.auth.AuthTasks
import com.dessoft.dogs.auth.AuthViewModel
import com.dessoft.dogs.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class AuthViewModelTest {

    @ExperimentalCoroutinesApi
    @get: Rule
    var dogCoroutineRule = DogCoroutineRule()
    private val TAG = AuthViewModelTest::class.java.simpleName

    /**
     * Agregar el de signUp
     * Agregar el de Main activity
     */

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

    @Test
    fun testResetErrors_Correct() {

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


        /**Validamos email*/
        viewModel.login("", "test1234")
        assertEquals(R.string.email_is_not_valid, viewModel.emailError.value)

        /**Validamos psw*/
        viewModel.login("hackaprende@gmail.com", "")
        assertEquals(
            R.string.password_must_not_be_empty,
            viewModel.passwordError.value
        )

        viewModel.resetErrors()
    }

    @Test
    fun testEmailPswSignUp_Empty() {

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

        /**Validamos email vacio*/
        viewModel.singUp("", "test1234", "test1234")
        assertEquals(R.string.email_is_not_valid, viewModel.emailError.value)

        /**Validamos psw  vacia*/
        viewModel.singUp("bisman.lemus@gmail.com", "", "test1234")
        assertEquals(
            R.string.password_must_not_be_empty.toString(),
            viewModel.passwordError.value.toString()
        )

        /**Validamos psw  vacia*/
        viewModel.singUp("bisman.lemus@gmail.com", "test1234", "")
        assertEquals(R.string.password_must_not_be_empty, viewModel.confirmPasswordError.value)


        /**Validamos psw  vacia*/
        viewModel.singUp("bisman.lemus@gmail.com", "test1234", "test123")
        assertEquals(R.string.passwords_do_not_match, viewModel.passwordError.value)

    }


    @Test
    fun testSignUp_Correct() {

        val fakeUser = User(1L, "lemus@gmail.com", "")
        val fakeUser2 = User(1L, "bisman@gmail.com", "")

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
                    fakeUser2
                )
            }
        }

        val viewModel = AuthViewModel(
            authRepository = FakeAuthRepository()
        )



        /**
         * En teoria con la clase de arriba
         * inicia sesion lemus
         * y devuelve a bisman
         *
         * y en lo que esta abajo compara, bisman es igual a bisman y si, assertTrue
         * si se compara lemus con bisman, van a ser diferentes, como el asserFalse
         * y se compara con bisman porque es el que regresa el signUp de arriba
         *
         * Validamos que los email son iguales*/
        viewModel.singUp("bisman.lemus@gmail.com", "test1234", "test1234")
        println("testSignUp_Correct: fakeUser2:${fakeUser2.email} viewModel:${viewModel.user.value?.email}")
        assertTrue(fakeUser2.email == viewModel.user.value?.email)

        assertFalse(fakeUser.email == viewModel.user.value?.email)
    }
}