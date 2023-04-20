package com.dessoft.dogs.dogdetail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import coil.annotation.ExperimentalCoilApi
import com.dessoft.dogs.R
import com.dessoft.dogs.dogdetail.ui.theme.DogsTheme
import com.dessoft.dogs.model.Dog

@ExperimentalCoilApi
class DogDetailComposeActivity : ComponentActivity() {
    companion object {
        const val DOG_KEY = "dog"
        const val IS_RECOGNITION_KEY = "is_recognition"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dog = intent?.extras?.getParcelable<Dog>(DOG_KEY)
        val isRecognition =
            intent?.extras?.getBoolean(IS_RECOGNITION_KEY, false) ?: false
        if (dog == null) {
            Toast.makeText(this, R.string.error_showing_dog_not_found, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setContent {
            DogsTheme {
                DogDetailScreen(dog = dog)
            }
        }
    }
}