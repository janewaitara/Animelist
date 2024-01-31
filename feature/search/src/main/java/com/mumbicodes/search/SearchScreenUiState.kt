package com.mumbicodes.search

import com.mumbicodes.model.data.Anime
import com.mumbicodes.model.data.Character

sealed interface SearchUiState<T> {
    object Loading : SearchUiState<Nothing>

    data class Results<T>(
        val data: T
    ) : SearchUiState<T>

    data class Error(val errorMessage: String) : SearchUiState<Nothing>

    object EmptyList : SearchUiState<Nothing>

    object EmptyState : SearchUiState<Nothing>
}

sealed interface AnimeSearchUiState {
    object Loading : AnimeSearchUiState

    data class AnimeResults(
        val data: List<Anime>
    ) : AnimeSearchUiState

    data class Error(val errorMessage: String) : AnimeSearchUiState

    object EmptyList : AnimeSearchUiState

    object EmptyState : AnimeSearchUiState
}

// TODO think of combining this into one interface and use T
sealed interface CharacterSearchUiState {
    object Loading : CharacterSearchUiState

    data class CharacterResults(
        val data: List<Character>
    ) : CharacterSearchUiState

    data class Error(val errorMessage: String) : CharacterSearchUiState

    object EmptyList : CharacterSearchUiState

    object EmptyState : CharacterSearchUiState
}

data class SearchScreenState(
    val searchParam: String = "",
    val searchMainFilter: SearchType = SearchType.ANIME,
    val characterSearchResultsState: CharacterSearchUiState = CharacterSearchUiState.EmptyState,
    val animeSearchResultsState: AnimeSearchUiState = AnimeSearchUiState.EmptyState
)