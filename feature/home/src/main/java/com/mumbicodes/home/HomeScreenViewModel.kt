package com.mumbicodes.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.mumbicodes.common.result.Result
import com.mumbicodes.domain.repository.AnimeRepository
import com.mumbicodes.domain.usecases.FetchYoutubeVideoStreamUrlUseCase
import com.mumbicodes.model.data.Anime
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val animeRepository: AnimeRepository,
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

        updateMediaItem("HkIKAnwLZCw")
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
                player.addMediaItem(
                    MediaItem.fromUri(it)
                )
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

    private val _animeSortType: MutableStateFlow<AnimeSortType> =
        MutableStateFlow(AnimeSortType.RECOMMENDED)
    val animeSortType = _animeSortType.asStateFlow()

    /**
     * Used when a user clicks see all on any of the sortList in the home page
     * The state is then used to know what list to display
     * */
    fun updateAnimeSortType(selectedAnimeSortType: AnimeSortType) {
        _animeSortType.value = selectedAnimeSortType
    }

    private val _selectedLayoutType: MutableStateFlow<SelectedLayoutType> =
        MutableStateFlow(SelectedLayoutType.LIST)
    val selectedLayout = _selectedLayoutType.asStateFlow()

    /**
     * Updated when a user toggles the layout type in the all categories screen
     * */
    fun updateSelectedLayoutType(selectedLayoutType: SelectedLayoutType) {
        _selectedLayoutType.value = selectedLayoutType
    }
}

enum class AnimeSortType {
    RECOMMENDED, TRENDING, POPULAR
}

enum class SelectedLayoutType {
    LIST, GRID
}