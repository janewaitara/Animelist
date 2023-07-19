package com.mumbicodes.anime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mumbicodes.common.result.Result
import com.mumbicodes.domain.repository.AnimeRepository
import com.mumbicodes.network.AnimeQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val animeRepository: AnimeRepository
) : ViewModel() {

    // TODO update the animeId to be the value passed to SavedState
    private val _animeDetails: StateFlow<AnimeDetailsScreenUiState> = getAnime(
        animeId = 0
    ).map { animeDetailsResult ->
        when (animeDetailsResult) {
            is Result.ApplicationError -> {
                AnimeDetailsScreenUiState.Error(animeDetailsResult.errors.joinToString())
            }

            is Result.Failure -> {
                AnimeDetailsScreenUiState.Error(animeDetailsResult.exception.toString())
            }

            Result.Loading -> {
                AnimeDetailsScreenUiState.Loading
            }

            is Result.Success -> {
                AnimeDetailsScreenUiState.AnimeDetails(
                    animeDetails = animeDetailsResult.data
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AnimeDetailsScreenUiState.Loading
    )

    private fun getAnime(animeId: Int): Flow<Result<AnimeQuery.Media>> {
        return animeRepository.getAnime(
            animeId = animeId,
            page = 0,
            perPage = 10
        )
    }
}