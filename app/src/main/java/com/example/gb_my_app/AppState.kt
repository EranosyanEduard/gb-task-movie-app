package com.example.gb_my_app

import com.example.gb_my_app.model.Movie

sealed class AppState {
    // Main classes.
    abstract class Success : AppState()

    data class Failure(val reason: Throwable) : AppState()

    object Loading : AppState()

    // Additional classes.
    data class MovieListFetched(val movieList: List<Movie>) : Success()
}