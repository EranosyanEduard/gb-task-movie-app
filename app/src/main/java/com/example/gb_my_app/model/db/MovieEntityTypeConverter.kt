package com.example.gb_my_app.model.db

import androidx.room.TypeConverter
import java.util.*

/**
 * Определить класс, который содержит ф-ии - конвертеры, использующиеся БД при
 * вставке и извлечении данных, исходные типы которых не являются примитивными.
 */
class MovieEntityTypeConverter {

    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.time

    @TypeConverter
    fun toDate(dateMs: Long?): Date? = dateMs?.let { Date(dateMs) }
}
