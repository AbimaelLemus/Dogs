package com.dessoft.dogs.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dessoft.dogs.api.ApiResponseStatus
import com.dessoft.dogs.auth.AuthNavDestinations.LoginScreenDestination
import com.dessoft.dogs.auth.AuthNavDestinations.SignUpScreenDestination
import com.dessoft.dogs.composables.ErrorDialog
import com.dessoft.dogs.composables.LoadingWheel
import com.dessoft.dogs.model.User

@Composable
fun AuthScreen(
    status: ApiResponseStatus<User>?,
    onLoginButtonClick: (String, String) -> Unit,
    onSignUpButtonClick: (email: String, password: String, passwordConfirmation: String) -> Unit,
    onErrorDialogDismiss: () -> Unit,
) {
    val navController = rememberNavController()
    AuthNavHost(
        navController = navController,
        onLoginButtonClick = onLoginButtonClick,
        onSignUpButtonClick = onSignUpButtonClick
    )

    //uso de programacion reactiva
    if (status is ApiResponseStatus.Loading) {
        LoadingWheel()
    } else if (status is ApiResponseStatus.Error) {
        ErrorDialog(messageId = status.messageId, onErrorDialogDismiss)
    }
}

@Composable
private fun AuthNavHost(
    navController: NavHostController,
    onLoginButtonClick: (String, String) -> Unit,
    onSignUpButtonClick: (email: String, password: String, passwordConfirmation: String) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = LoginScreenDestination
    ) {
        composable(route = LoginScreenDestination) {
            LoginScreen(
                onLoginButtonClick = onLoginButtonClick,
                onRegisterButtonClick = {
                    navController.navigate(route = SignUpScreenDestination)
                }
            )
        }
        composable(route = SignUpScreenDestination) {
            SingUpScreen(
                onSignUpButtonClick = onSignUpButtonClick,
                onNavigationIconClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}