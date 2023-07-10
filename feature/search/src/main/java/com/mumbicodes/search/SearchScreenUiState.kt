package com.mumbicodes.search

import com.mumbicodes.network.SearchAnimeQuery

sealed interface AnimeSearchUiState {
    object Loading : AnimeSearchUiState

    data class AnimeResults(
        val data: List<SearchAnimeQuery.Medium>
    ) : AnimeSearchUiState

    data class Error(val errorMessage: String) : AnimeSearchUiState
}