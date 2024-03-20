package com.mumbicodes.ui.controller

import androidx.media3.common.Player
import kotlinx.coroutines.flow.StateFlow

enum class PlayerState {
    LOADING, BUFFERING, READY_TO_PLAY, ENDEND
}

/**
 * Holds the state of the player
 * */
data class PlayerControllerState(
    val isVideoPlaying: Boolean = false,
    val isVolumeOn: Boolean = false,
    val videoHasEnded: Boolean = false,
    val playerState: PlayerState = PlayerState.LOADING
)

/**
 * A singleton to handle the Player
 * */
interface PlayerController {
    /**
     * Holds the state of the Player
     * */
    val playerState: StateFlow<PlayerControllerState>

    /**
     * A Singleton of the Player used in the overall app
     * */
    val controllerPlayer: Player

    /**
     * Toggles the play and pause states of the player*/
    fun playOrPause()

    /**
     * Toggles the player audio volume*/
    fun toggleAudioState()

    /**
     * Removes the mediaItem at index 0 and adds a new one at the same index
     * Prepares the player and plays when ready
     * */
    fun updateMediaItem(streamUrl: String)

    /**
     * Restarts the Player from 0 ms*/
    fun replay()

    /**
     * Releases the player.*/
    fun destroy()

    /**
     * Stops playback */
    fun stop()
}