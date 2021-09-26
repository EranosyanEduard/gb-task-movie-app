package com.example.gb_my_app.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gb_my_app.AppState
import com.example.gb_my_app.model.Movie
import com.example.gb_my_app.model.RepositoryImpl
import com.example.gb_my_app.model.db.MovieEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val EXCEPTION_INVALID_MOVIE_ID_TEXT = "Недопустимый идентификатор фильма"

class MovieViewModel : ViewModel() {

    /**
     * Лайв-дата - публичный интерфейс для контроля за состоянием данных.
     */
    val movieLiveData: MutableLiveData<AppState> = MutableLiveData()

    private val repository = RepositoryImpl.getInstance()

    private val cbForGetMovieById = object : Callback<Movie> {
        /**
         * Обработать успешный результат запроса.
         */
        override fun onResponse(
            call: Call<Movie>,
            response: Response<Movie>
        ) {
            val fetchedResponseBody: Movie = response.body() ?: Movie()

            movieLiveData.postValue(AppState.MovieFetched(fetchedResponseBody))
        }

        /**
         * Обработать неудачный результат запроса.
         */
        override fun onFailure(call: Call<Movie>, t: Throwable) {
            movieLiveData.postValue(AppState.Failure(t))
        }
    }

    /**
     * Добавить фильм в базу данных приложения (публичный интерфейс).
     */
    fun addMovie(movie: MovieEntity) = repository.addMovie(movie)

    /**
     * Извлечь подробную информцию о фильме по его идентификатору (публичный интерфейс).
     *
     * @param movieID идентификатор фильма.
     */
    fun getMovieById(movieID: Int?) = getMovieByIdFromRepository(movieID)

    private fun getMovieByIdFromRepository(movieID: Int?) {
        movieLiveData.value = AppState.Loading

        if (movieID == null) {
            movieLiveData.postValue(AppState.Failure(Throwable(EXCEPTION_INVALID_MOVIE_ID_TEXT)))
            return
        }

        repository.getMovieById(movieID, cbForGetMovieById)
    }
}
