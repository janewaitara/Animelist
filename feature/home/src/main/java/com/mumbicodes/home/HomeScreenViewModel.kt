package com.mumbicodes.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mumbicodes.common.result.Result
import com.mumbicodes.domain.repository.AnimeRepository
import com.mumbicodes.network.AnimeListQuery
import com.mumbicodes.network.RecommendationsQuery
import com.mumbicodes.network.type.MediaFormat
import com.mumbicodes.network.type.MediaSort
import com.mumbicodes.network.type.MediaType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor
(private val animeRepository: AnimeRepository) : ViewModel() {

    private val recommendedAnimes: Flow<Result<List<RecommendationsQuery.Recommendation>>> =
        animeRepository.getRecommendations()

    private var _recommendedUiState: StateFlow<RecommendedAnimesUiStates> =
        recommendedAnimes.map {
            when (it) {
                is Result.ApplicationError -> {
                    RecommendedAnimesUiStates.Error(it.errors.joinToString())
                }

                is Result.Failure -> {
                    RecommendedAnimesUiStates.Error(it.toString())
                }

                Result.Loading -> {
                    Log.e("Data", "KLoaing")
                    RecommendedAnimesUiStates.Loading
                }

                is Result.Success -> {
                    Log.e("Data 6", it.data.first().media?.title?.english ?: "No data")
                    RecommendedAnimesUiStates.RecommendedAnimes(recommended = it.data)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RecommendedAnimesUiStates.Loading
        )

    val recommendedUiState: StateFlow<RecommendedAnimesUiStates> = _recommendedUiState

    // TODO think of a better way to manage the data classes
    private val popularAnimes: Flow<Result<List<AnimeListQuery.Medium>>> =
        animeRepository.getAnimeList(
            page = 0,
            perPage = 30,
            type = MediaType.ANIME,
            sortList = listOf(MediaSort.TRENDING),
            formatIn = listOf(
                MediaFormat.MOVIE,
                MediaFormat.MUSIC,
                MediaFormat.TV,
                MediaFormat.SPECIAL,
                MediaFormat.MANGA
            )
        )

    private var _popularUiState: StateFlow<PopularAnimeStates> =
        popularAnimes.map {
            when (it) {
                is Result.ApplicationError -> {
                    PopularAnimeStates.Error(it.errors.joinToString())
                }

                is Result.Failure -> {
                    PopularAnimeStates.Error(it.toString())
                }

                Result.Loading -> {
                    Log.e("Data", "KLoaing")
                    PopularAnimeStates.Loading
                }

                is Result.Success -> {
                    // Log.e("Data 6", it.data.first().media?.title?.english ?: "No data")
                    PopularAnimeStates.PopularAnimes(popular = it.data)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PopularAnimeStates.Loading
        )

    val popularUiState: StateFlow<PopularAnimeStates> = _popularUiState
}