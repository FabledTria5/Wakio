package dev.fabled.repository.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.fabled.domain.repository.AppPreferencesRepository
import dev.fabled.repository.utils.Constants.FIRST_LAUNCH_KEY
import dev.fabled.repository.utils.datastore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class AppPreferencesRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context
) : AppPreferencesRepository {

    private object PreferencesKeys {
        val firstLaunchKey = booleanPreferencesKey(name = FIRST_LAUNCH_KEY)
    }

    private val dataStore = context.datastore

    override suspend fun persistLaunchState() {
        dataStore.edit { preference ->
            preference[PreferencesKeys.firstLaunchKey] = false
        }
    }

    override suspend fun isFirstLaunch() = dataStore.data
        .catch { exception ->
            Timber.e(exception)

            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }
        .map { preference ->
            preference[PreferencesKeys.firstLaunchKey] ?: true
        }
        .first()
}