package com.dessoft.dogs.dogdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dessoft.dogs.R
import com.dessoft.dogs.model.Dog

@Composable
fun DogDetailScreen() {

    Box(
        modifier = Modifier
            .background(colorResource(id = R.color.secondary_background))
            .padding(start = 8.dp, end = 8.dp, bottom = 16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        val dog =
            Dog(
                1L, 20, "Bilco", "Bilco", "Herding", "70",
                "75", "", "10-15", "Friendly, playful", "Amigable, juguetón",
                "5", "6", false
            )

        DogInformation(dog)

    }

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
                    text = stringResource(
                        id = R.string.dog_index_format,
                        dog.index
                    ),
                    fontSize = 32.sp,
                    color = colorResource(
                        id = R.color.text_black
                    ),
                    textAlign = TextAlign.End
                )
            }

        }

    }
}

@Preview
@Composable
fun DogDetailScreenPreview() {
    DogDetailScreen()
}
