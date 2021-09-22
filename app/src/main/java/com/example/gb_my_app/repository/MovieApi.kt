package com.example.gb_my_app.repository

import com.example.gb_my_app.model.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("now_playing")
    fun fetchMovieListNowPlaying(
        @Query("api_key")
        apiKey: String,

        @Query("language")
        lang: String,

        @Query("region")
        region: String

    ): Call<MovieApiResponse.MovieListNowPlaying>

    @GET("{movieID}")
    fun fetchMovieById(
        @Path("movieID")
        movieID: Int,

        @Query("api_key")
        apiKey: String,

        @Query("language")
        lang: String,

        @Query("region")
        region: String

    ): Call<Movie>
}
