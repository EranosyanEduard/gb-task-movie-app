package com.example.gb_my_app.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
/**
 * Класс определяет структуру таблицы в БД.
 */
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val adult: Boolean,

    val date: Date = Date(),

    val movieId: Int,

    val originalTitle: String,

    val overview: String,

    val posterPath: String,

    val releaseDate: String,

    val title: String,

    val userComment: String,

    val voteAverage: Double
)
