package com.mumbicodes.anime

import androidx.media3.common.Player
import com.mumbicodes.model.data.Anime
import com.mumbicodes.ui.controller.PlayerControllerState

sealed interface AnimeDetailsScreenUiState {
    object Loading : AnimeDetailsScreenUiState

    data class AnimeDetails(
        val animeDetails: Anime
    ) : AnimeDetailsScreenUiState

    data class Error(val errorMessage: String) : AnimeDetailsScreenUiState
}

data class AnimeDetailsScreenState(
    val animeDetails: AnimeDetailsScreenUiState = AnimeDetailsScreenUiState.Loading,
    val playerControllerState: PlayerControllerState = PlayerControllerState(),
    val player: Player
)