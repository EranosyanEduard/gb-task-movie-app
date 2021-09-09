package com.example.gb_my_app.ui.view

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.gb_my_app.AppState
import com.example.gb_my_app.model.Repository

class MovieViewModel : ViewModel() {

    private val repository: Repository = Repository.getInstance()

    private val movieIdLiveData = MutableLiveData<Int>()

    private val movieLiveData: MutableLiveData<AppState> = MutableLiveData()

    private val movieLiveDataWatcher = Transformations
        .switchMap(movieIdLiveData) {
            repository.getMovieByID(it)
        }

    fun getMovieLiveData(): LiveData<AppState> = movieLiveData

    fun getMovie(lifecycleOwner: LifecycleOwner, movieID: Int?) =
        getMovieById(lifecycleOwner, movieID)

    private fun getMovieById(lifecycleOwner: LifecycleOwner, movieID: Int?) {
        movieLiveData.value = AppState.Loading

        movieID?.also {
            movieIdLiveData.value = it
        }

        movieLiveDataWatcher.observe(lifecycleOwner, { movie ->
            movieLiveData.value = AppState.MovieFetched(movie)
        })
    }
}