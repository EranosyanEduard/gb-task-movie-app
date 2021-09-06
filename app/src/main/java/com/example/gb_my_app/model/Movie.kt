package com.example.gb_my_app.model

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,

    @SerializedName("original_title")
    val originalTitle: String,

    val overview: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("release_date")
    val releaseDate: String,

    val title: String,

    @SerializedName("vote_average")
    val voteAverage: String,
)