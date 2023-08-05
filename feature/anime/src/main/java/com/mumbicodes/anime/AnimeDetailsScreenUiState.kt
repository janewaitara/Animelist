package com.mumbicodes.anime

import com.mumbicodes.model.data.Anime

sealed interface AnimeDetailsScreenUiState {
    object Loading : AnimeDetailsScreenUiState

    data class AnimeDetails(
        val animeDetails: Anime
    ) : AnimeDetailsScreenUiState

    data class Error(val errorMessage: String) : AnimeDetailsScreenUiState
}