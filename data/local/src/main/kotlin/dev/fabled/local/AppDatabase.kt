package dev.fabled.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.fabled.local.dao.AlarmsDao
import dev.fabled.local.dao.ArticlesDao
import dev.fabled.local.dao.QuotesDao
import dev.fabled.local.entities.AlarmEntity
import dev.fabled.local.entities.ArticleEntity
import dev.fabled.local.entities.QuoteEntity

@Database(
    entities = [QuoteEntity::class, ArticleEntity::class, AlarmEntity::class],
    version = 5,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun quotesDao(): QuotesDao
    abstract fun articlesDao(): ArticlesDao
    abstract fun alarmsDao(): AlarmsDao

}