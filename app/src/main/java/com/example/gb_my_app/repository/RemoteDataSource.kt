package com.example.gb_my_app.repository

import com.example.gb_my_app.model.Movie
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    private val remoteApi: MovieApi

    private val baseUrl = "https://api.themoviedb.org/3/movie/"

    enum class UrlEnum(val url: String) {
        Image("https://image.tmdb.org/t/p/w200/")
    }

    // Определить общие параметры запроса
    private val queryParamApiKey = "c7dbc5f1d33bf032496be926b19514e6"
    private val queryParamLanguage = "ru-RU"
    private val queryParamRegion = "RU"

    init {
        // Реализовать интерфейс MovieApi с помощью пакета retrofit
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .build()

        remoteApi = retrofit.create(MovieApi::class.java)
    }

    /**
     * Извлечь список фильмов категории "Сейчас в кино" из удаленного источника.
     *
     * @param cb ф-я - обработчик ответа, реализующая интерфейс Callback пакета retrofit.
     */
    fun fetchMovieListNowPlaying(cb: Callback<MovieApiResponse.MovieListNowPlaying>) {
        remoteApi
            .fetchMovieListNowPlaying(queryParamApiKey, queryParamLanguage, queryParamRegion)
            .enqueue(cb)
    }

    /**
     * Извлечь подробную информацию о фильме.
     *
     * @param movieId идентификатор фильма.
     */
    fun fetchMovieById(movieId: Int, cb: Callback<Movie>) {
        remoteApi
            .fetchMovieById(movieId, queryParamApiKey, queryParamLanguage, queryParamRegion)
            .enqueue(cb)
    }
}
