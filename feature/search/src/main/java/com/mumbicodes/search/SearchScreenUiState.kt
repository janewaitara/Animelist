package com.mumbicodes.search

import com.mumbicodes.model.data.Anime
import com.mumbicodes.network.SearchCharacterQuery

sealed interface AnimeSearchUiState {
    object Loading : AnimeSearchUiState

    data class AnimeResults(
        val data: List<Anime>
    ) : AnimeSearchUiState

    data class Error(val errorMessage: String) : AnimeSearchUiState
}

sealed interface CharacterSearchUiState {
    object Loading : CharacterSearchUiState

    data class CharacterResults(
        val data: List<SearchCharacterQuery.Character>
    ) : CharacterSearchUiState

    data class Error(val errorMessage: String) : CharacterSearchUiState
}