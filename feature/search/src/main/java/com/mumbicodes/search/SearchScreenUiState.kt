package com.mumbicodes.search

import com.mumbicodes.model.data.Anime
import com.mumbicodes.model.data.Character

sealed interface AnimeSearchUiState {
    object Loading : AnimeSearchUiState

    data class AnimeResults(
        val data: List<Anime>
    ) : AnimeSearchUiState

    data class Error(val errorMessage: String) : AnimeSearchUiState

    object EmptyList : AnimeSearchUiState
}

sealed interface CharacterSearchUiState {
    object Loading : CharacterSearchUiState

    data class CharacterResults(
        val data: List<Character>
    ) : CharacterSearchUiState

    data class Error(val errorMessage: String) : CharacterSearchUiState

    object EmptyList : CharacterSearchUiState
}

data class SearchScreenState(
    val searchParam: String = "",
    val searchMainFilter: SearchType = SearchType.ANIME,
    val characterSearchResultsState: CharacterSearchUiState = CharacterSearchUiState.Loading,
    val animeSearchResultsState: AnimeSearchUiState = AnimeSearchUiState.Loading
)