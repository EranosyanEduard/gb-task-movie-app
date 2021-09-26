package com.example.gb_my_app.model

import androidx.lifecycle.LiveData
import com.example.gb_my_app.model.db.MovieEntity
import com.example.gb_my_app.repository.MovieApiResponse
import retrofit2.Callback

interface Repository {
    fun getMovieListNowPlaying(cb: Callback<MovieApiResponse.MovieListNowPlaying>)

    fun getMovieById(movieId: Int, cb: Callback<Movie>)

    fun addMovie(movie: MovieEntity)

    fun getMovieListCommented(): LiveData<List<MovieEntity>>
}
