package com.mumbicodes.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mumbicodes.common.result.Result
import com.mumbicodes.domain.repository.AnimeRepository
import com.mumbicodes.domain.repository.TrailerRepository
import com.mumbicodes.domain.usecases.FetchYoutubeVideoStreamUrlUseCase
import com.mumbicodes.model.data.Anime
import com.mumbicodes.model.data.LocalMediaFormat
import com.mumbicodes.model.data.LocalMediaSort
import com.mumbicodes.model.data.LocalMediaType
import com.mumbicodes.ui.controller.PlayerController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val animeRepository: AnimeRepository,
    private val trailerRepository: TrailerRepository,
    private val playerController: PlayerController,
    private val fetchYoutubeVideoStreamUrlUseCase: FetchYoutubeVideoStreamUrlUseCase
) : ViewModel() {

    private val _homeState: MutableStateFlow<HomeScreenState> =
        MutableStateFlow(HomeScreenState(player = playerController.controllerPlayer))
    val homeState = _homeState.asStateFlow()

    init {
        updatePlayerState()
    }
    private fun updatePlayerState() {
        viewModelScope.launch {
            playerController.playerState.collectLatest { playerState ->
                _homeState.value = homeState.value.copy(
                    playerControllerState = playerState
                )
            }
        }
    }

    /**
     * Called when a user swipes on the view pager
     * */
    private fun updateMediaItem(animeTrailerId: String) {
        Log.d("Media trailer", animeTrailerId)

        // TODO check whether the youtube id is present and if the source is youtube.
        viewModelScope.launch {
            val streamUrl = fetchYoutubeVideoStreamUrlUseCase(animeTrailerId)
            Log.d("Youtube Stream", streamUrl ?: "No url")
            streamUrl?.let {
                playerController.updateMediaItem(it)
            }
        }
    }

    fun onPlayPauseClicked() = playerController.playOrPause()

    fun replayVideo() = playerController.replay()

    fun toggleAudioStateVideo() = playerController.toggleAudioState()

    private val recommendedAnimes: Flow<Result<List<Anime>>> =
        animeRepository.getRecommendations()

    private val popularAnimes: Flow<Result<List<Anime>>> =
        animeRepository.getAnimeList(
            page = 0,
            perPage = 30,
            type = LocalMediaType.ANIME,
            sortList = listOf(LocalMediaSort.POPULARITY_DESC),
            formatIn = listOf(
                LocalMediaFormat.MOVIE,
                // LocalMediaFormat.MUSIC,
                LocalMediaFormat.TV,
                // LocalMediaFormat.SPECIAL,
                LocalMediaFormat.MANGA
            )
        )

    private val trendingAnimes: Flow<Result<List<Anime>>> =
        animeRepository.getAnimeList(
            page = 0,
            perPage = 30,
            type = LocalMediaType.ANIME,
            sortList = listOf(LocalMediaSort.TRENDING_DESC),
            formatIn = listOf(
                LocalMediaFormat.MOVIE,
                LocalMediaFormat.TV
            )
        )

    init {
        observeTrendingAnimes()
        observePopularAnimes()
        observeRecommendedAnimes()
    }

    private fun observeTrendingAnimes() {
        viewModelScope.launch {
            trendingAnimes.collectLatest {
                when (it) {
                    is Result.ApplicationError -> {
                        _homeState.value = homeState.value.copy(
                            trendingAnimesUiState =
                            AnimeUiStates.Error(it.errors.joinToString())
                        )
                    }

                    is Result.Failure -> {
                        _homeState.value = homeState.value.copy(
                            trendingAnimesUiState =
                            AnimeUiStates.Error(it.toString())
                        )
                    }

                    Result.Loading -> {
                        _homeState.value = homeState.value.copy(
                            trendingAnimesUiState =
                            AnimeUiStates.Loading
                        )
                    }

                    is Result.Success -> {
                        _homeState.value = homeState.value.copy(
                            trendingAnimesUiState = AnimeUiStates.Success(animes = it.data),
                            trendingAnimes = it.data.toMutableList()
                        )
                        homeState.value.trendingAnimes.first().trailer?.id?.let { trailerId ->
                            updateMediaItem(trailerId)
                        }
                    }
                }
            }
        }
    }

    private fun observePopularAnimes() {
        viewModelScope.launch {
            popularAnimes.collectLatest {
                when (it) {
                    is Result.ApplicationError -> {
                        _homeState.value = homeState.value.copy(
                            popularAnimesUiState = AnimeUiStates.Error(it.errors.joinToString())
                        )
                    }

                    is Result.Failure -> {
                        _homeState.value = homeState.value.copy(
                            popularAnimesUiState = AnimeUiStates.Error(it.toString())
                        )
                    }

                    Result.Loading -> {
                        _homeState.value = homeState.value.copy(
                            popularAnimesUiState = AnimeUiStates.Loading
                        )
                    }

                    is Result.Success -> {
                        _homeState.value = homeState.value.copy(
                            popularAnimesUiState = AnimeUiStates.Success(animes = it.data)
                        )
                    }
                }
            }
        }
    }

    private fun observeRecommendedAnimes() {
        viewModelScope.launch {
            recommendedAnimes.collectLatest {
                when (it) {
                    is Result.ApplicationError -> {
                        _homeState.value = homeState.value.copy(
                            recommendedAnimesUiStates = AnimeUiStates.Error(it.errors.joinToString())
                        )
                    }

                    is Result.Failure -> {
                        _homeState.value = homeState.value.copy(
                            recommendedAnimesUiStates = AnimeUiStates.Error(it.toString())
                        )
                    }

                    Result.Loading -> {
                        _homeState.value = homeState.value.copy(
                            recommendedAnimesUiStates = AnimeUiStates.Loading
                        )
                    }

                    is Result.Success -> {
                        _homeState.value = homeState.value.copy(
                            recommendedAnimesUiStates = AnimeUiStates.Success(
                                animes = it.data
                            )
                        )
                    }
                }
            }
        }
    }

    /**
     * Used when a user clicks see all on any of the sortList in the home page
     * The state is then used to know what list to display
     * */
    fun updateAnimeSortType(selectedAnimeSortType: AnimeSortType) {
        _homeState.value = homeState.value.copy(
            animeSortType = selectedAnimeSortType
        )
    }

    /**
     * Updated when a user toggles the layout type in the all categories screen
     * */
    fun updateSelectedLayoutType(selectedLayoutType: SelectedLayoutType) {
        _homeState.value = homeState.value.copy(
            selectedLayoutType = selectedLayoutType
        )
    }

    fun updateTrendingListOnSwipe(swipedAnimeId: Int) {
        val anime = homeState.value.trendingAnimes.find {
            it.id == swipedAnimeId
        } ?: return
        val list = homeState.value.trendingAnimes.toMutableList()
        list.remove(anime)
        list.add(anime)
        list.toList()

        _homeState.value = homeState.value.copy(
            trendingAnimes = list
        )

        playerController.stop()
        homeState.value.trendingAnimes.first().trailer?.id?.let {
            updateMediaItem(it)
        }
    }

    fun saveAnimeTrailerPosition() {
        viewModelScope.launch {
            trailerRepository.updateTrailerPosition(playerController.controllerPlayer.currentPosition)
        }
    }
}