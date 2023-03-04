package dev.fabled.local.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.fabled.local.AppDatabase
import dev.fabled.local.dao.AlarmsDao
import dev.fabled.local.dao.ArticlesDao
import dev.fabled.local.dao.QuotesDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase = Room
        .databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "application_database"
        )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideQuotesDao(database: AppDatabase): QuotesDao = database.quotesDao()

    @Provides
    @Singleton
    fun provideArticlesDao(database: AppDatabase): ArticlesDao = database.articlesDao()

    @Provides
    @Singleton
    fun provideAlarmsDao(database: AppDatabase): AlarmsDao = database.alarmsDao()

}