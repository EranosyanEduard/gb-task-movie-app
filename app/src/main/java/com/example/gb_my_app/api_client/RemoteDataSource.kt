package com.example.gb_my_app.api_client

import com.example.gb_my_app.model.Actor
import com.example.gb_my_app.model.Movie
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    private val actorRemoteApi: ActorApi
    private val movieRemoteApi: MovieApi

    enum class UrlEnum(val url: String) {
        Base("https://api.themoviedb.org/3/"),
        ActorRelativeBase("person/"),
        MovieRelativeBase("movie/"),
        Image("https://image.tmdb.org/t/p/w200/")
    }

    // Определить общие параметры запроса
    private val queryParamApiKey = "c7dbc5f1d33bf032496be926b19514e6"
    private val queryParamLanguage = "ru-RU"
    private val queryParamRegion = "RU"

    init {
        // Создать экземпляры api-клиентов
        val createAbsoluteUrl: (url: String) -> String = { "${UrlEnum.Base.url}$it" }

        val createRetrofit: (url: String) -> Retrofit = {
            Retrofit.Builder()
                .baseUrl(it)
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().setLenient().create()
                    )
                )
                .build()
        }

        val (actorAsRetrofit, movieAsRetrofit) = listOf(
            UrlEnum.ActorRelativeBase.url,
            UrlEnum.MovieRelativeBase.url
        )
            .map(createAbsoluteUrl)
            .map(createRetrofit)

        actorRemoteApi = actorAsRetrofit.create(ActorApi::class.java)
        movieRemoteApi = movieAsRetrofit.create(MovieApi::class.java)
    }

    /**
     * Извлечь список актеров категории "Популярные" из удаленного источника.
     *
     * @param cb ф-я - обработчик ответа, реализующая интерфейс Callback пакета retrofit.
     */
    fun fetchActorListPopular(cb: Callback<ActorApiResponse.ActorListPopular>) {
        actorRemoteApi
            .fetchActorListPopular(queryParamApiKey, queryParamLanguage, 1)
            .enqueue(cb)
    }

    /**
     * Извлечь подробную информацию об актере.
     *
     * @param actorId идентификатор актера.
     */
    fun fetchActorById(actorId: Int, cb: Callback<Actor>) {
        actorRemoteApi
            .fetchActorById(actorId, queryParamApiKey, queryParamLanguage)
            .enqueue(cb)
    }

    /**
     * Извлечь список фильмов категории "Сейчас в кино" из удаленного источника.
     *
     * @param cb ф-я - обработчик ответа, реализующая интерфейс Callback пакета retrofit.
     */
    fun fetchMovieListNowPlaying(cb: Callback<MovieApiResponse.MovieListNowPlaying>) {
        movieRemoteApi
            .fetchMovieListNowPlaying(queryParamApiKey, queryParamLanguage, queryParamRegion)
            .enqueue(cb)
    }

    /**
     * Извлечь подробную информацию о фильме.
     *
     * @param movieId идентификатор фильма.
     */
    fun fetchMovieById(movieId: Int, cb: Callback<Movie>) {
        movieRemoteApi
            .fetchMovieById(movieId, queryParamApiKey, queryParamLanguage, queryParamRegion)
            .enqueue(cb)
    }
}
