package com.mumbicodes.domain.repository

import kotlinx.coroutines.flow.Flow

interface TrailerRepository {
    val trailerCurrentPosition: Flow<Long>

    suspend fun updateTrailerPosition(trailerPosition: Long)
}