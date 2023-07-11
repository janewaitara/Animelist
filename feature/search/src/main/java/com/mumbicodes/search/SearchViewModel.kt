package com.mumbicodes.search

import com.mumbicodes.domain.repository.SearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) {
    private val _searchMainFilterUiState: MutableStateFlow<SearchType> =
        MutableStateFlow(SearchType.ANIME)
    val searchMainFilterUiState = _searchMainFilterUiState.asStateFlow()

    /**
     * Updates the main search item user is searching for
     * */
    fun updateSearchFilter(userSearchMainFilter: SearchType) {
        _searchMainFilterUiState.value = userSearchMainFilter
    }
}

enum class SearchType {
    ANIME, CHARACTER
}