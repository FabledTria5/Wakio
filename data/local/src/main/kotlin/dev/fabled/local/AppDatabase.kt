package dev.fabled.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.fabled.local.dao.AlarmsDao
import dev.fabled.local.dao.ArticlesDao
import dev.fabled.local.dao.QuotesDao
import dev.fabled.local.entities.AlarmEntity
import dev.fabled.local.entities.ArticleEntity
import dev.fabled.local.entities.QuoteEntity
import dev.fabled.local.utils.Converters

@Database(
    entities = [QuoteEntity::class, ArticleEntity::class, AlarmEntity::class],
    version = 6,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun quotesDao(): QuotesDao
    abstract fun articlesDao(): ArticlesDao
    abstract fun alarmsDao(): AlarmsDao

}