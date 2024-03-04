package com.mumbicodes.data.repository

import com.mumbicodes.domain.repository.TrailerRepository
import com.mumbicodes.local.preferences.TrailerPreferences
import kotlinx.coroutines.flow.Flow

class TrailerRepositoryImpl(
    private val trailerPreferences: TrailerPreferences
) : TrailerRepository {
    override val trailerCurrentPosition: Flow<Long>
        get() = trailerPreferences.trailerCurrentPosition

    override suspend fun updateTrailerPosition(trailerPosition: Long) {
        trailerPreferences.updateTrailerPosition(trailerPosition)
    }
}