package com.example.gb_my_app.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gb_my_app.model.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.themoviedb.org/3/movie/"

private const val TAG_EXCEPTION = "ОШИБКА"
private const val EXCEPTION_MESSAGE = "Во время выполнения запроса произошла ошибка"

class HttpClient {

    private val appApi: AppApi

    init {
        // Implement AppApi interface.
        val retrofit: Retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        appApi = retrofit.create(AppApi::class.java)
    }

    /**
     * Извлечь список фильмов категории "Сейчас в кино" из удаленного источника.
     *
     * @return список фильмов, обернутый в LiveData.
     */
    fun fetchMovieListNowPlaying(): LiveData<List<Movie>> {
        val responseLiveData = MutableLiveData<List<Movie>>()

        appApi
            .fetchMovieListNowPlaying()
            .enqueue(object : Callback<AppApiResponse.FetchMovieListNowPlaying> {

                override fun onResponse(
                    call: Call<AppApiResponse.FetchMovieListNowPlaying>,
                    response: Response<AppApiResponse.FetchMovieListNowPlaying>,
                ) {
                    val movieList = response.body()?.movieList ?: emptyList()
                    responseLiveData.value = movieList
                }

                override fun onFailure(
                    call: Call<AppApiResponse.FetchMovieListNowPlaying>,
                    t: Throwable,
                ) {
                    val apiName = "fetchMovieListNowPlaying"
                    Log.d(TAG_EXCEPTION, "$apiName: $EXCEPTION_MESSAGE", t)
                }
            })

        return responseLiveData
    }

    /**
     * Извлечь подробную информацию о фильме.
     *
     * @param movieId идентификатор фильма.
     * @return фильм.
     */
    fun fetchMovie(movieId: Int): LiveData<Movie> {
        val responseLiveData = MutableLiveData<Movie>()

        appApi
            .fetchMovie(movieId)
            .enqueue(object : Callback<Movie> {

                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    val movie = response.body() ?: Movie()
                    responseLiveData.value = movie
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    val apiName = "fetchMovie"
                    Log.d(TAG_EXCEPTION, "$apiName: $EXCEPTION_MESSAGE", t)
                }
            })

        return responseLiveData
    }
}