package com.dessoft.dogs

data class Dog(
    val id: Long,
    val index: Int,
    val nameEn: String,
    val nameEs: String,
    val type: String,
    val heightFemale: Double,
    val heightMale: Double,
    val imageUrl: String,
    val lifeExpectancy: String,
    val temperament: String,
    val temperamentEn: String,
    val weightFemale: String,
    val weightMale: String
)