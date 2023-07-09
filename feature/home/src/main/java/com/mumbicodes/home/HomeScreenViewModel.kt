package com.mumbicodes.home

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
                    RecommendedAnimesUiStates.Loading
                }

                is Result.Success -> {
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
            sortList = listOf(MediaSort.POPULARITY),
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
                    PopularAnimeStates.Loading
                }

                is Result.Success -> {
                    PopularAnimeStates.PopularAnimes(popular = it.data)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PopularAnimeStates.Loading
        )

    val popularUiState: StateFlow<PopularAnimeStates> = _popularUiState

    private val trendingAnimes: Flow<Result<List<AnimeListQuery.Medium>>> =
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

    private var _trendingUiState: StateFlow<TrendingAnimeStates> =
        trendingAnimes.map {
            when (it) {
                is Result.ApplicationError -> {
                    TrendingAnimeStates.Error(it.errors.joinToString())
                }

                is Result.Failure -> {
                    TrendingAnimeStates.Error(it.toString())
                }

                Result.Loading -> {
                    TrendingAnimeStates.Loading
                }

                is Result.Success -> {
                    TrendingAnimeStates.TrendingAnimes(trending = it.data)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TrendingAnimeStates.Loading
        )

    val trendingUiState: StateFlow<TrendingAnimeStates> = _trendingUiState

    private val _animeSortType: MutableStateFlow<AnimeSortType> = MutableStateFlow(AnimeSortType.RECOMMENDED)
    val animeSortType = _animeSortType.asStateFlow()

    /**
     * Used when a user clicks see all on any of the sortList in the home page
     * The state is then used to know what list to display
     * */
    fun updateAnimeSortType(selectedAnimeSortType: AnimeSortType) {
        _animeSortType.value = selectedAnimeSortType
    }
}

enum class AnimeSortType {
    RECOMMENDED, TRENDING, POPULAR
}