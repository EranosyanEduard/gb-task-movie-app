package com.example.gb_my_app.model

import android.content.Context
import com.example.gb_my_app.repository.MovieApiResponse
import com.example.gb_my_app.repository.RemoteDataSource
import retrofit2.Callback

private const val EXCEPTION_MESSAGE = "Создайте репозиторий, вызвав метод create"

/**
 * Класс простого репозитория, реализующий паттерн "одиночка".
 *
 * Использовать метод create(), чтобы создать единственный экземпляр класса.
 * Прим: экземпляр создается в файле App.kt.
 */
class RepositoryImpl private constructor(context: Context) : Repository {

    companion object {
        private var instance: Repository? = null

        fun create(context: Context): Repository {
            if (instance == null) {
                instance = RepositoryImpl(context)
            }

            return instance as Repository
        }

        fun getInstance() = instance ?: throw Exception(EXCEPTION_MESSAGE)
    }

    private val remoteDataSource = RemoteDataSource()

    override fun getMovieListNowPlaying(cb: Callback<MovieApiResponse.MovieListNowPlaying>) {
        remoteDataSource.fetchMovieListNowPlaying(cb)
    }

    override fun getMovieById(movieId: Int, cb: Callback<Movie>) {
        remoteDataSource.fetchMovieById(movieId, cb)
    }
}
