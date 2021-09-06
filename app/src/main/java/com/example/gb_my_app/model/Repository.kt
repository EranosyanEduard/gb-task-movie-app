package com.example.gb_my_app.model

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.gb_my_app.api.HttpClient

private const val EXCEPTION_MESSAGE = "Создайте репозиторий, вызвав метод create"

/**
 * A simple Repository class (singleton).
 *
 * Use the create() method to create an instance of this repository.
 */
class Repository private constructor(context: Context) {

    companion object {
        private var instance: Repository? = null

        fun create(context: Context) {
            if (instance == null) {
                instance = Repository(context)
            }
        }

        fun getInstance() = instance ?: throw Exception(EXCEPTION_MESSAGE)
    }

    private val httpClient: HttpClient = HttpClient()

    fun getMovieListNowPlaying(): LiveData<List<Movie>> = httpClient.fetchMovieListNowPlaying()
}