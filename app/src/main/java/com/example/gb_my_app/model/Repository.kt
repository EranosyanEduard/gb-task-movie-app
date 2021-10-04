package com.example.gb_my_app.model

import androidx.lifecycle.LiveData
import com.example.gb_my_app.api_client.ActorApiResponse
import com.example.gb_my_app.api_client.MovieApiResponse
import com.example.gb_my_app.model.db.MovieEntity
import retrofit2.Callback

interface Repository {
    fun getActorListPopular(cb: Callback<ActorApiResponse.ActorListPopular>)

    fun getActorById(actorId: Int, cb: Callback<Actor>)

    fun getMovieListNowPlaying(cb: Callback<MovieApiResponse.MovieListNowPlaying>)

    fun getMovieById(movieId: Int, cb: Callback<Movie>)

    fun addMovie(movie: MovieEntity)

    fun getMovieListCommented(): LiveData<List<MovieEntity>>
}
