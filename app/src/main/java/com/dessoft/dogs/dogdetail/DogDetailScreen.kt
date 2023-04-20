package com.dessoft.dogs.dogdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.dessoft.dogs.R
import com.dessoft.dogs.api.ApiResponseStatus
import com.dessoft.dogs.model.Dog

@ExperimentalCoilApi
@Composable
fun DogDetailScreen(
    dog: Dog,
    status: ApiResponseStatus<Any>? = null,
    onButtonClicked: () -> Unit,
    onErrorDialogDismiss: () -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.secondary_background))
            .padding(start = 8.dp, end = 8.dp, bottom = 16.dp),
        contentAlignment = Alignment.TopCenter
    ) {

        DogInformation(dog)

        Image(
            modifier = Modifier
                .width(270.dp)
                .padding(top = 80.dp),
            painter = rememberImagePainter(dog.imageUrl),
            contentDescription = dog.nameEs
        )

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            onClick = { onButtonClicked() }) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = ""
            )
        }

        //uso de programacion reactiva
        if (status is ApiResponseStatus.Loading) {
            LoadingWheel()
        } else if (status is ApiResponseStatus.Error) {
            ErrorDialog(status = status, onErrorDialogDismiss)
        }

    }

}

@Composable
fun LoadingWheel() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Color.Green
        )
    }
}

@Composable
fun ErrorDialog(
    status: ApiResponseStatus.Error<Any>,
    onDialogDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(stringResource(id = R.string.error_dialog_title))
        },
        text = {
            Text(stringResource(id = status.messageId))
        },
        confirmButton = {
            Button(onClick = { onDialogDismiss() }) {
                Text(stringResource(id = R.string.try_again))
            }
        }
    )
}

@Composable
fun DogInformation(dog: Dog) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 180.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            color = colorResource(id = android.R.color.white)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "#" + dog.index,
                    fontSize = 32.sp,
                    color = colorResource(
                        id = R.color.text_black
                    ),
                    textAlign = TextAlign.End
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
                    text = dog.nameEs,
                    color = colorResource(id = R.color.text_black),
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium
                )

                LifeIcon()

                Text(
                    stringResource(id = R.string.dog_life_expectancy_format, dog.lifeExpectancy),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.text_black)
                )

                Text(
                    text = dog.temperament,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.text_black),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Divider(
                    modifier = Modifier
                        .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
                    color = colorResource(id = R.color.divider),
                    thickness = 1.dp
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DogDateColumn(
                        modifier = Modifier.weight(1f),
                        stringResource(id = R.string.female),
                        dog.weightFemale,
                        dog.heightFemale
                    )

                    verticalDivider()

                    Column(
                        modifier = Modifier
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = dog.type,
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.text_black),
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(top = 8.dp),
                        )

                        Text(
                            text = stringResource(id = R.string.group),
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.dark_gray),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    verticalDivider()

                    DogDateColumn(
                        modifier = Modifier.weight(1f),
                        stringResource(id = R.string.female),
                        dog.weightMale,
                        dog.heightMale
                    )


                }

            }

        }

    }
}

@Composable
fun LifeIcon() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 80.dp, end = 80.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = colorResource(id = R.color.color_primary)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_hearth_white),
                contentDescription = "null",
                tint = Color.White,
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .padding(4.dp)
            )
        }

        Surface(
            shape = RoundedCornerShape(bottomEnd = 2.dp, topEnd = 2.dp),
            modifier = Modifier
                .width(200.dp)
                .height(6.dp),
            color = colorResource(id = R.color.color_primary)
        ) {

        }

    }
}

@Composable
private fun verticalDivider() {
    Divider(
        modifier = Modifier
            .height(42.dp)
            .width(1.dp),
        color = colorResource(id = R.color.divider)

    )
}

@Composable
private fun DogDateColumn(
    modifier: Modifier = Modifier,
    gen: String,
    weight: String,
    height: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = gen,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.text_black),
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = weight,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = colorResource(id = R.color.text_black),
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = stringResource(id = R.string.weight),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = colorResource(id = R.color.dark_gray)
        )

        Text(
            text = height,
            modifier = Modifier.padding(top = 8.dp),
            color = colorResource(id = R.color.text_black),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
        )

        Text(
            text = stringResource(id = R.string.height),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = colorResource(id = R.color.dark_gray)
        )

    }
}

@Preview
@Composable
fun DogDetailScreenPreview() {

    val dog =
        Dog(
            1L, 20, "Bilco", "Bilco", "Herding", "70",
            "75", "", "10-15", "Friendly, playful", "Amigable, juguetón",
            "5", "6", false
        )

    DogDetailScreen(dog = dog, onButtonClicked = {}, onErrorDialogDismiss = {})
}
