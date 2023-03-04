package dev.fabled.wakio.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.fabled.domain.repository.AlarmsRepository
import dev.fabled.domain.repository.AppPreferencesRepository
import dev.fabled.domain.repository.ArticlesRepository
import dev.fabled.domain.repository.AuthorizationRepository
import dev.fabled.domain.repository.ErrorsRepository
import dev.fabled.domain.repository.QuotesRepository
import dev.fabled.domain.utils.ApplicationUtil
import dev.fabled.navigation.navigation_core.Navigator
import dev.fabled.navigation.navigation_core.NavigatorImpl
import dev.fabled.repository.preferences.AppPreferencesRepositoryImpl
import dev.fabled.repository.repository.AlarmsRepositoryImpl
import dev.fabled.repository.repository.ArticlesRepositoryImpl
import dev.fabled.repository.repository.AuthorizationRepositoryImpl
import dev.fabled.repository.repository.ErrorsRepositoryImpl
import dev.fabled.repository.repository.QuotesRepositoryImpl
import dev.fabled.repository.utils.AndroidApplicationUtil
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SingletonBindings {

    @Binds
    @Singleton
    fun bindNavigator(navigatorImpl: NavigatorImpl): Navigator

    @Binds
    @Singleton
    fun bindQuotesRepository(quotesRepositoryImpl: QuotesRepositoryImpl): QuotesRepository

    @Binds
    @Singleton
    fun bindArticlesRepository(articlesRepositoryImpl: ArticlesRepositoryImpl): ArticlesRepository

    @Binds
    @Singleton
    fun bindAlarmsRepository(alarmsRepositoryImpl: AlarmsRepositoryImpl): AlarmsRepository

    @Binds
    @Singleton
    fun bindAuthRepository(authorizationRepositoryImpl: AuthorizationRepositoryImpl): AuthorizationRepository

    @Binds
    @Singleton
    fun bindPreferencesRepository(appPreferencesRepositoryImpl: AppPreferencesRepositoryImpl): AppPreferencesRepository

    @Binds
    @Singleton
    fun bindErrorsRepository(errorsRepositoryImpl: ErrorsRepositoryImpl): ErrorsRepository

    @Binds
    @Singleton
    fun bindAppUtil(applicationUtil: AndroidApplicationUtil): ApplicationUtil

}