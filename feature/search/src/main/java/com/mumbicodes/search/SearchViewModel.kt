package com.mumbicodes.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mumbicodes.common.result.Result
import com.mumbicodes.domain.repository.SearchRepository
import com.mumbicodes.model.data.Anime
import com.mumbicodes.model.data.Character
import com.mumbicodes.model.data.LocalMediaFormat
import com.mumbicodes.model.data.LocalMediaSort
import com.mumbicodes.model.data.LocalMediaType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _searchParam: MutableStateFlow<String> = MutableStateFlow("")
    val searchParam = _searchParam.asStateFlow()

    private val _searchMainFilterUiState: MutableStateFlow<SearchType> =
        MutableStateFlow(SearchType.ANIME)
    val searchMainFilterUiState = _searchMainFilterUiState.asStateFlow()

    val characterSearchResultsState: StateFlow<CharacterSearchUiState> = searchCharacter(
        searchParam = searchParam.value ?: ""
    ).map {
        when (it) {
            is Result.ApplicationError -> {
                CharacterSearchUiState.Error(it.errors.joinToString())
            }

            is Result.Failure -> {
                CharacterSearchUiState.Error(it.exception.message.toString())
            }

            Result.Loading -> {
                CharacterSearchUiState.Loading
            }

            is Result.Success -> {
                CharacterSearchUiState.CharacterResults(
                    data = it.data
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CharacterSearchUiState.Loading
    )

    val animeSearchResultsState: StateFlow<AnimeSearchUiState> = searchAnime(
        searchParam = searchParam.value ?: ""
    ).map {
        when (it) {
            is Result.ApplicationError -> {
                AnimeSearchUiState.Error(it.errors.joinToString())
            }

            is Result.Failure -> {
                AnimeSearchUiState.Error(it.exception.message.toString())
            }

            Result.Loading -> {
                AnimeSearchUiState.Loading
            }

            is Result.Success -> {
                AnimeSearchUiState.AnimeResults(
                    data = it.data
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AnimeSearchUiState.Loading
    )

    /**
     * Updates the main search item user is searching for
     * */
    fun updateSearchFilter(userSearchMainFilter: SearchType) {
        _searchMainFilterUiState.value = userSearchMainFilter
    }

    /**
     * Called everytime the user types on the search input field*/
    fun onSearchParameterChanged(searchParam: String) {
        _searchParam.value = searchParam
    }

    private fun searchCharacter(searchParam: String): Flow<Result<List<Character>>> =
        searchRepository.searchCharacter(searchParam = searchParam)

    private fun searchAnime(searchParam: String): Flow<Result<List<Anime>>> =
        searchRepository.searchAnime(
            searchParam = searchParam,
            type = LocalMediaType.ANIME,
            sortList = listOf(
                LocalMediaSort.TRENDING,
                LocalMediaSort.POPULARITY,
                LocalMediaSort.FORMAT,
                LocalMediaSort.TYPE
            ),
            formatIn = listOf(
                LocalMediaFormat.MOVIE,
                LocalMediaFormat.MUSIC,
                LocalMediaFormat.TV,
                LocalMediaFormat.SPECIAL,
                LocalMediaFormat.MANGA
            )
        )
}

enum class SearchType {
    ANIME, CHARACTER
}