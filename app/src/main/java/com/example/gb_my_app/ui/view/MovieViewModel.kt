package com.example.gb_my_app.ui.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.gb_my_app.model.Movie
import com.example.gb_my_app.model.Repository

class MovieViewModel : ViewModel() {

    private val repository: Repository = Repository.getInstance()

    private val movieIdLiveData = MutableLiveData<Int>()

    val movieLiveData: LiveData<Movie> = Transformations
        .switchMap(movieIdLiveData) {
            repository.getMovieByID(it)
        }

    fun getMovieById(movieID: Int?) {
        if (movieID != null) {
            movieIdLiveData.value = movieID!!
        }
    }
}