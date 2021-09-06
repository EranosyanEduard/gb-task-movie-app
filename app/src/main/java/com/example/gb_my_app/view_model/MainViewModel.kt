package com.example.gb_my_app.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.gb_my_app.model.Movie
import com.example.gb_my_app.model.Repository

class MainViewModel : ViewModel() {

    private val repository: Repository = Repository.getInstance()

    val movieListLiveData: LiveData<List<Movie>> = repository.getMovieListNowPlaying()
}