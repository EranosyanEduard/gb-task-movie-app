package com.example.gb_my_app.ui.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.gb_my_app.model.Movie
import com.example.gb_my_app.model.RepositoryImpl
import com.example.gb_my_app.utils.toMovie

class CommentedMovieViewModel : ViewModel() {

    private val repository = RepositoryImpl.getInstance()

    private val movieEntityListLiveData = repository.getMovieListCommented()

    val movieListLiveData: LiveData<List<Movie>> =
        Transformations.switchMap(movieEntityListLiveData) { movieEntityList ->
            MutableLiveData(movieEntityList.map { it.toMovie() })
        }
}
