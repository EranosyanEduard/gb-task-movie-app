package com.example.gb_my_app.model

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int = -1,

    @SerializedName("original_title")
    val originalTitle: String = "Оригинальное название",

    val overview: String = "Сюжет",

    @SerializedName("poster_path")
    val posterPath: String = "Постер",

    @SerializedName("release_date")
    val releaseDate: String = "Релиз",

    val title: String = "Название",

    @SerializedName("vote_average")
    val voteAverage: Double = .0,
)