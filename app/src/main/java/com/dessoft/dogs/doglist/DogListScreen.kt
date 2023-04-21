package com.dessoft.dogs.doglist

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.dessoft.dogs.R
import com.dessoft.dogs.api.ApiResponseStatus
import com.dessoft.dogs.composables.BackNavigationIcon
import com.dessoft.dogs.composables.ErrorDialog
import com.dessoft.dogs.composables.LoadingWheel
import com.dessoft.dogs.model.Dog

private const val GRID_SPAN_COUNT = 3

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalCoilApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun DogListScreen(
    onNavigationIconClick: () -> Unit,
    dogList: List<Dog>,
    onDogClicked: (Dog) -> Unit,
    status: ApiResponseStatus<Any>? = null,
    onErrorDialogDismiss: () -> Unit,
) {
    Scaffold(
        topBar = { DogListScreenTopBar(onNavigationIconClick) }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(GRID_SPAN_COUNT),
            content = {
                items(dogList) {
                    DogGridItem(dog = it, onDogClicked = onDogClicked)
                }
            }
        )
    }

    //uso de programacion reactiva
    if (status is ApiResponseStatus.Loading) {
        LoadingWheel()
    } else if (status is ApiResponseStatus.Error) {
        ErrorDialog(messageId = status.messageId, onErrorDialogDismiss)
    }

}

//TODO(Abimael): mejorar DogGridItem y dejar como estaba el otro dogList

//v71 el lazy, hace lo mismo que el recycler
/*LazyColumn {
    items(dogList) {
        DogItem(dog = it, onDogClicked = onDogClicked)
    }
}*/

@Composable
fun DogListScreenTopBar(onClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.my_dog_Collection))
        },
        backgroundColor = Color.White,
        contentColor = Color.Black,
        navigationIcon = { BackNavigationIcon(onClick) }
    )
}

@Composable
fun DogItem(dog: Dog, onDogClicked: (Dog) -> Unit) {
    if (dog.inCollection) {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .clickable { onDogClicked(dog) },
            text = dog.nameEs
        )
    } else {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .background(color = Color.Green),
            text = stringResource(id = R.string.dog_index_format, dog.index)
        )
    }
}

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun DogGridItem(dog: Dog, onDogClicked: (Dog) -> Unit) {
    if (dog.inCollection) {
        Surface(
            modifier = Modifier
                .padding(8.dp)
                .height(100.dp)
                .width(100.dp),
            onClick = { onDogClicked(dog) },
            shape = RoundedCornerShape(4.dp)
        ) {
            Image(
                painter = rememberImagePainter(dog.imageUrl),
                contentDescription = null,
                modifier = Modifier.background(Color.White)
            )
        }
    } else {
        Surface(
            modifier = Modifier
                .padding(8.dp)
                .height(100.dp)
                .width(100.dp),
            color = Color.Green,
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = dog.index.toString(),
                color = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                fontSize = 42.sp,
                fontWeight = FontWeight.Black
            )
        }
    }

}