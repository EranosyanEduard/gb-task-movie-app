package com.example.gb_my_app.api

import com.example.gb_my_app.model.Movie
import com.google.gson.annotations.SerializedName

class AppApiResponse {

    @SerializedName("results")
    lateinit var movieList: List<Movie>
}