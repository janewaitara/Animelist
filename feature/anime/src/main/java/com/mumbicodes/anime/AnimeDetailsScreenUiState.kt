package com.mumbicodes.anime

import com.mumbicodes.network.AnimeQuery

sealed interface AnimeDetailsScreenUiState {
    object Loading : AnimeDetailsScreenUiState

    data class AnimeDetails(
        val animeDetails: AnimeQuery.Media
    ) : AnimeDetailsScreenUiState

    data class Error(val errorMessage: String) : AnimeDetailsScreenUiState
}