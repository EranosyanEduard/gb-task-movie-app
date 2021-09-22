package com.example.gb_my_app.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gb_my_app.AppState
import com.example.gb_my_app.model.Movie
import com.example.gb_my_app.model.RepositoryImpl
import com.example.gb_my_app.repository.MovieApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    /**
     * Лайв-дата - публичный интерфейс для контроля за состоянием данных.
     */
    val movieListLiveData: MutableLiveData<AppState> = MutableLiveData()

    private val repository = RepositoryImpl.getInstance()

    private val cbForGetMovieListNowPlaying =
        object : Callback<MovieApiResponse.MovieListNowPlaying> {
            /**
             * Обработать успешный результат запроса.
             */
            override fun onResponse(
                call: Call<MovieApiResponse.MovieListNowPlaying>,
                response: Response<MovieApiResponse.MovieListNowPlaying>
            ) {
                val fetchedResponseBody: List<Movie> = response.body()?.movieList ?: emptyList()

                movieListLiveData.postValue(AppState.MovieListFetched(fetchedResponseBody))
            }

            /**
             * Обработать неудачный результат запроса.
             */
            override fun onFailure(call: Call<MovieApiResponse.MovieListNowPlaying>, t: Throwable) {
                movieListLiveData.postValue(AppState.Failure(t))
            }
        }

    /**
     * Извлечь список фильмов из удаленного источника (публичный интерфейс).
     */
    fun getMovieListNowPlaying() = getMovieListNowPlayingFromRepository()

    private fun getMovieListNowPlayingFromRepository() {
        movieListLiveData.value = AppState.Loading
        repository.getMovieListNowPlaying(cbForGetMovieListNowPlaying)
    }
}
