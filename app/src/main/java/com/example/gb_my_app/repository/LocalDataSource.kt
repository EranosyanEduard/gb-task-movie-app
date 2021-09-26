package com.example.gb_my_app.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.gb_my_app.model.db.Db
import com.example.gb_my_app.model.db.MovieDao
import com.example.gb_my_app.model.db.MovieEntity
import java.util.concurrent.Executor
import java.util.concurrent.Executors

private const val DB_NAME = "gb_my_app"

class LocalDataSource(context: Context) {

    private val db: Db = Room.databaseBuilder(
        context.applicationContext,
        Db::class.java,
        DB_NAME
    ).build()

    private val movieDao: MovieDao = db.movieDao()

    // Создать "Исполнителя" для асинхронной работы
    private val executor: Executor = Executors.newSingleThreadExecutor()

    fun addMovie(movie: MovieEntity) = executor.execute { movieDao.create(movie) }

    fun fetchMovieListCommented(): LiveData<List<MovieEntity>> = movieDao.readAll()
}
