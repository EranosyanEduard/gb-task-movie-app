package com.example.gb_my_app.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gb_my_app.AppState
import com.example.gb_my_app.model.Repository

class MainViewModel : ViewModel() {

    private val repository: Repository = Repository.getInstance()

    private val movieListLiveData: MutableLiveData<AppState> = MutableLiveData()

    fun getMovieListLiveData(): LiveData<AppState> = movieListLiveData

    fun getMovieList(lifecycleOwner: LifecycleOwner) = getMovieListNowPlaying(lifecycleOwner)

    private fun getMovieListNowPlaying(lifecycleOwner: LifecycleOwner) {
        movieListLiveData.value = AppState.Loading

        repository
            .getMovieListNowPlaying()
            .observe(lifecycleOwner, { movieList ->
                movieListLiveData.value = AppState.MovieListFetched(movieList)
            })
    }
}