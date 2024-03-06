package com.mumbicodes.home

import androidx.media3.common.Player
import com.mumbicodes.model.data.Anime
import com.mumbicodes.ui.controller.PlayerControllerState

// TODO create a model class and mapper for the different classes to remove the network from the features

sealed interface AnimeUiStates<out T> {
    object Loading : AnimeUiStates<Nothing>

    data class Success<T>(
        val animes: T
    ) : AnimeUiStates<T>

    data class Error(val errorMessage: String) : AnimeUiStates<Nothing>
}

/*enum class PlayerState {
    LOADING, BUFFERING, READY_TO_PLAY, ENDEND
}*/

enum class AnimeSortType {
    RECOMMENDED, TRENDING, POPULAR
}

enum class SelectedLayoutType {
    LIST, GRID
}

/**
 * @param trendingAnimes is the state used by trailers and is often updated when a trailer is swiped
 * @param trendingAnimesUiState is the state we are observing from the data sources and is now manually updated
 * */
data class HomeScreenState(
    /* val isVideoPlaying: Boolean = false,
     val isVolumeOn: Boolean = true,
     val player: Player,
     val playerState: PlayerState = PlayerState.LOADING,*/
    val playerControllerState: PlayerControllerState = PlayerControllerState(),
    val player: Player,
    val trendingAnimes: List<Anime> = mutableListOf(),
    val trendingAnimesUiState: AnimeUiStates<List<Anime>> = AnimeUiStates.Loading,
    val recommendedAnimesUiStates: AnimeUiStates<List<Anime>> = AnimeUiStates.Loading,
    val popularAnimesUiState: AnimeUiStates<List<Anime>> = AnimeUiStates.Loading,
    val selectedLayoutType: SelectedLayoutType = SelectedLayoutType.LIST,
    val animeSortType: AnimeSortType = AnimeSortType.RECOMMENDED
)