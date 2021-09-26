package com.example.gb_my_app.model.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
/**
 * Определить интерфейс, который содержит ф-ии для взаимодействия с БД.
 */
interface MovieDao {

    /**
     * Использовать LiveData для выполнения запросов в фоновом потоке, а
     * их результаты в основном.
     */

    @Query("SELECT * FROM MovieEntity")
    fun readAll(): LiveData<List<MovieEntity>>

    @Insert
    fun create(movie: MovieEntity)
}
