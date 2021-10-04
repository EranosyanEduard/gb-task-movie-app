package com.example.gb_my_app.api_client

import com.example.gb_my_app.model.Movie
import com.google.gson.annotations.SerializedName

sealed class MovieApiResponse {

    class MovieListNowPlaying : MovieApiResponse() {
        @SerializedName("results")
        lateinit var movieList: List<Movie>
    }
}
