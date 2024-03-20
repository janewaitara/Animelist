package com.mumbicodes.local.preferences

import kotlinx.coroutines.flow.Flow

/**
 * @property trailerCurrentPosition
 * returns the trailer's position in which we use to seek in the anime details screen.
 *
 * @property updateTrailerPosition
 * saves the anime trailer's position when navigating from the home screen to the anime screen
 * to ensure the trailer continues playing from the position it was in the home screen during the click action.
 * */
interface TrailerPreferences {
    val trailerCurrentPosition: Flow<Long>

    suspend fun updateTrailerPosition(trailerPosition: Long)
}