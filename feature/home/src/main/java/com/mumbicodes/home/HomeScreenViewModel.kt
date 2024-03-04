package com.mumbicodes.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.mumbicodes.common.result.Result
import com.mumbicodes.domain.repository.AnimeRepository
import com.mumbicodes.domain.repository.TrailerRepository
import com.mumbicodes.domain.usecases.FetchYoutubeVideoStreamUrlUseCase
import com.mumbicodes.model.data.Anime
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
class HomeScreenViewModel @Inject constructor(
    private val animeRepository: AnimeRepository,
    private val trailerRepository: TrailerRepository,
    private val player: Player,
    private val fetchYoutubeVideoStreamUrlUseCase: FetchYoutubeVideoStreamUrlUseCase
) : ViewModel() {

    private val _homeState: MutableStateFlow<HomeScreenState> =
        MutableStateFlow(HomeScreenState(player = player))
    val homeState = _homeState.asStateFlow()

    // Listening to the Player's playback events
    private val playerListener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            _homeState.value = homeState.value.copy(
                isVideoPlaying = isPlaying
            )
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            when (playbackState) {
                Player.STATE_BUFFERING -> {
                    _homeState.value = homeState.value.copy(
                        playerState = PlayerState.BUFFERING
                    )
                }

                Player.STATE_ENDED -> {
                    _homeState.value = homeState.value.copy(
                        playerState = PlayerState.ENDEND
                    )
                }

                Player.STATE_IDLE -> {
                    _homeState.value = homeState.value.copy(
                        playerState = PlayerState.LOADING
                    )
                }

                Player.STATE_READY -> {
                    _homeState.value = homeState.value.copy(
                        playerState = PlayerState.READY_TO_PLAY
                    )
                }
            }
        }
    }

    init {
        player.addListener(playerListener)
    }

    /**
     * Called when a user swipes on the view pager
     * TODO - what happens on the first video - also, how is the list update on swipe
     * */
    fun updateMediaItem(animeTrailerId: String) {
        Log.d("Media trailer", animeTrailerId)

        // TODO check whether the youtube id is present and if the source is youtube.
        viewModelScope.launch {
            val streamUrl = fetchYoutubeVideoStreamUrlUseCase(animeTrailerId)
            Log.d("Youtube Stream", streamUrl ?: "No url")
            streamUrl?.let {
                player.removeMediaItem(0)
                player.addMediaItem(
                    MediaItem.fromUri(it)
                )

                player.volume = 0f
                player.prepare()
                player.play()
                player.apply {
                    this.playWhenReady = true
                }
            }
        }
    }

    fun onPlayPauseClicked() {
        if (player.isPlaying) {
            player.pause()
        } else {
            player.play()
        }
    }

    fun replayVideo() {
        player.seekTo(0)
        player.playWhenReady = true
    }

    fun toggleAudioStateVideo() = if (player.volume == 0f) {
        _homeState.value = homeState.value.copy(
            isVolumeOn = true
        )
        player.volume = 1f
    } else {
        _homeState.value = homeState.value.copy(
            isVolumeOn = false
        )
        player.volume = 0f
    }

    private val recommendedAnimes: Flow<Result<List<Anime>>> =
        animeRepository.getRecommendations()

    private val popularAnimes: Flow<Result<List<Anime>>> =
        animeRepository.getAnimeList(
            page = 0,
            perPage = 30,
            type = LocalMediaType.ANIME,
            sortList = listOf(LocalMediaSort.POPULARITY),
            formatIn = listOf(
                LocalMediaFormat.MOVIE,
                LocalMediaFormat.MUSIC,
                LocalMediaFormat.TV,
                LocalMediaFormat.SPECIAL,
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
                LocalMediaFormat.MUSIC,
                LocalMediaFormat.TV,
                LocalMediaFormat.SPECIAL,
                LocalMediaFormat.MANGA
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

        player.stop()
        homeState.value.trendingAnimes.first().trailer?.id?.let {
            updateMediaItem(it)
        }
    }

    fun saveAnimeTrailerPosition() {
        viewModelScope.launch {
            trailerRepository.updateTrailerPosition(player.currentPosition)
        }
    }
}