package com.mumbicodes.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class TrailerPreferencesImpl(
    private val dataStore: DataStore<Preferences>
) : TrailerPreferences {

    object Keys {
        val trailerPosition = longPreferencesKey("TRAILER_POSITION")
    }

    override val trailerCurrentPosition: Flow<Long>
        get() = dataStore.data.mapLatest {
            it[Keys.trailerPosition] ?: 0
        }

    override suspend fun updateTrailerPosition(trailerPosition: Long) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[Keys.trailerPosition] = trailerPosition
        }
    }
}