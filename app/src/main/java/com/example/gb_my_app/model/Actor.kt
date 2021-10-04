package com.example.gb_my_app.model

import com.google.gson.annotations.SerializedName

data class Actor(
    val id: Int = -1,

    val birthday: String = "Дата рождения",

    val name: String = "Имя",

    @SerializedName("place_of_birth")
    val placeOfBirth: String = "Место рождения",

    val popularity: Double = .0,
)
