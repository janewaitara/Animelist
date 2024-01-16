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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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

    private val _characterSearchResultsState: MutableStateFlow<CharacterSearchUiState> =
        MutableStateFlow(CharacterSearchUiState.Loading)
    val characterSearchResultsState = _characterSearchResultsState.asStateFlow()

    private val _animeSearchResultsState: MutableStateFlow<AnimeSearchUiState> =
        MutableStateFlow(AnimeSearchUiState.Loading)
    val animeSearchResultsState = _animeSearchResultsState.asStateFlow()

    fun onSearchClicked() {
        viewModelScope.launch {
            when (searchMainFilterUiState.value) {
                SearchType.ANIME -> {
                    searchAnime(searchParam.value).collectLatest {
                        _animeSearchResultsState.value = when (it) {
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
                    }
                }

                SearchType.CHARACTER -> {
                    searchCharacter(searchParam.value).collectLatest {
                        _characterSearchResultsState.value = when (it) {
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
                    }
                }
            }
        }
    }

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