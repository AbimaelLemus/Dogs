package com.dessoft.dogs.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import coil.annotation.ExperimentalCoilApi
import com.dessoft.dogs.dogdetail.ui.theme.DogsTheme
import com.dessoft.dogs.main.MainActivity
import com.dessoft.dogs.model.User
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val user = viewModel.user

            val userValue = user.value
            if (userValue != null) {
                User.setLoggedInUser(this, userValue)
                startMainActivity()
            }

            val status = viewModel.status

            DogsTheme {
                AuthScreen(
                    status = status.value,
                    onLoginButtonClick = { email, password ->
                        viewModel.login(email, password)
                    },
                    onSignUpButtonClick = { email, password, passwordConfirmation ->
                        viewModel.singUp(email, password, passwordConfirmation)
                    },
                    onErrorDialogDismiss = ::resetApiResponseStatus,
                    authViewModel = viewModel
                )
            }
        }
    }

    private fun resetApiResponseStatus() {
        viewModel.resetApiResponseStatus()
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


}