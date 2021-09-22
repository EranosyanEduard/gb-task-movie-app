package com.example.gb_my_app.model

import com.example.gb_my_app.repository.MovieApiResponse
import retrofit2.Callback

interface Repository {
    fun getMovieListNowPlaying(cb: Callback<MovieApiResponse.MovieListNowPlaying>)

    fun getMovieById(movieId: Int, cb: Callback<Movie>)
}
