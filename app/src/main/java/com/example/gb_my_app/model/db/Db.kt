package com.example.gb_my_app.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MovieEntity::class], version = 1)
@TypeConverters(MovieEntityTypeConverter::class)
/**
 * Определить абстракцию БД.
 */
abstract class Db : RoomDatabase() {

    // Реализовать интерфейс MovieDao
    abstract fun movieDao(): MovieDao
}
