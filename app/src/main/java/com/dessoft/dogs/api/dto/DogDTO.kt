package com.dessoft.dogs.api.dto

import com.squareup.moshi.Json

class DogDTO( //DataTransferObject
    val id: Long,
    val index: Int,
    @field:Json(name = "name_en") val nameEn: String,
    @field:Json(name = "name_es") val nameEs: String,
    @field:Json(name = "dog_type") val type: String,
    @field:Json(name = "height_female") val heightFemale: Double,
    @field:Json(name = "height_male") val heightMale: Double,
    @field:Json(name = "image_url") val imageUrl: String,
    @field:Json(name = "life_expectancy") val lifeExpectancy: String,
    val temperament: String,
    @field:Json(name = "temperament_en") val temperamentEn: String,
    @field:Json(name = "weight_female") val weightFemale: String,
    @field:Json(name = "weight_male") val weightMale: String
)