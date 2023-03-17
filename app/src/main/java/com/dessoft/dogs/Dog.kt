package com.dessoft.dogs

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dog(
    val id: Long,
    val index: Int,
    val nameEn: String,
    val nameEs: String,
    val type: String,
    val heightFemale: String,
    val heightMale: String,
    val imageUrl: String,
    val lifeExpectancy: String,
    val temperament: String,
    val temperamentEn: String,
    val weightFemale: String,
    val weightMale: String
) : Parcelable