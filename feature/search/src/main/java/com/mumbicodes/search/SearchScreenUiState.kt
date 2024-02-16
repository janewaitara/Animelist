package com.mumbicodes.search

import com.mumbicodes.model.data.Anime
import com.mumbicodes.model.data.Character

sealed interface SearchUiState<out T> {
    object Loading : SearchUiState<Nothing>

    data class Results<T>(
        val data: T
    ) : SearchUiState<T>

    data class Error(val errorMessage: String) : SearchUiState<Nothing>

    object EmptyList : SearchUiState<Nothing>

    object EmptyState : SearchUiState<Nothing>
}

data class SearchScreenState(
    val searchParam: String = "",
    val searchMainFilter: SearchType = SearchType.ANIME,
    val characterSearchResultsState: SearchUiState<List<Character>> = SearchUiState.EmptyState,
    val animeSearchResultsState: SearchUiState<List<Anime>> = SearchUiState.EmptyState
)