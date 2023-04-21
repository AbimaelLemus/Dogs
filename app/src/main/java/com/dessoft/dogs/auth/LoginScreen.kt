package com.dessoft.dogs.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dessoft.dogs.R
import com.dessoft.dogs.api.ApiResponseStatus

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    status: ApiResponseStatus<Any>? = null,
) {

    Scaffold(topBar = { LoginScreenToolbar() }) {
        Content()
    }

}

@Composable
fun Content() {

    val email = remember {
        mutableStateOf("")
    }



    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(value = email.value, onValueChange = { email.value = it })

    }
}

@Composable
fun LoginScreenToolbar() {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.app_name))
        },
        backgroundColor = Color.Red,
        contentColor = Color.White,
    )
}