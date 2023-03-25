package dev.fabled.local.utils

import androidx.room.TypeConverter
import java.time.LocalDateTime

object Converters {

    @TypeConverter
    fun fromLocalDateTime(localDateTime: LocalDateTime): String = localDateTime.toString()

    @TypeConverter
    fun toLocalDateTime(string: String): LocalDateTime = LocalDateTime.parse(string)

}