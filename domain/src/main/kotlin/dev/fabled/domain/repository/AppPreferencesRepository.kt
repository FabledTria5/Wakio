package dev.fabled.domain.repository

interface AppPreferencesRepository {

    suspend fun persistLaunchState()

    suspend fun isFirstLaunch(): Boolean

}