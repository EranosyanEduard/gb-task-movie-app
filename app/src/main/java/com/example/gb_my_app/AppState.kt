package com.example.gb_my_app

import com.example.gb_my_app.model.Movie

sealed class AppState {
    // Main classes.
    data class Failure(val reason: Throwable) : AppState()

    object Loading : AppState()

    abstract class Success : AppState()

    // Additional classes.
    data class MovieListFetched(val movieList: List<Movie>) : Success()

    data class MovieFetched(val movie: Movie) : Success()
}