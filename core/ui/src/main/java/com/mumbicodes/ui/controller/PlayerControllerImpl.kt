package com.mumbicodes.ui.controller

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PlayerControllerImpl(private val player: Player) : PlayerController {

    private val _playerState: MutableStateFlow<PlayerControllerState> = MutableStateFlow(
        PlayerControllerState()
    )

    override val playerState: StateFlow<PlayerControllerState> = _playerState
    override val controllerPlayer: Player
        get() = player

    init {
        observePlayerState()
    }

    private fun observePlayerState() {
        // Listening to the Player's playback events
        player.addListener(
            object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)
                    _playerState.value = playerState.value.copy(
                        isVideoPlaying = isPlaying
                    )
                }

                override fun onPlaybackStateChanged(playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)
                    when (playbackState) {
                        Player.STATE_BUFFERING -> {
                            _playerState.value = playerState.value.copy(
                                playerState = PlayerState.BUFFERING,
                                videoHasEnded = false
                            )
                        }

                        Player.STATE_ENDED -> {
                            _playerState.value = playerState.value.copy(
                                playerState = PlayerState.ENDEND,
                                videoHasEnded = true
                            )
                        }

                        Player.STATE_IDLE -> {
                            _playerState.value = playerState.value.copy(
                                playerState = PlayerState.LOADING,
                                videoHasEnded = false
                            )
                        }

                        Player.STATE_READY -> {
                            _playerState.value = playerState.value.copy(
                                playerState = PlayerState.READY_TO_PLAY,
                                videoHasEnded = false
                            )
                        }
                    }
                }
            }
        )
    }

    override fun playOrPause() {
        if (player.isPlaying) {
            player.pause()
        } else {
            player.play()
        }
    }

    override fun toggleAudioState() =
        if (player.volume == 0f) {
            _playerState.value = playerState.value.copy(
                isVolumeOn = true
            )
            player.volume = 1f
        } else {
            _playerState.value = playerState.value.copy(
                isVolumeOn = false
            )
            player.volume = 0f
        }

    override fun updateMediaItem(streamUrl: String) {
        player.removeMediaItem(0)
        player.addMediaItem(
            MediaItem.fromUri(streamUrl)
        )

        player.volume = 0f
        player.prepare()
        player.play()
        player.apply {
            this.playWhenReady = true
        }
    }

    override fun replay() {
        player.seekTo(0)
        player.playWhenReady = true
    }

    override fun destroy() {
        player.release()
    }

    override fun stop() {
        player.stop()
    }
}