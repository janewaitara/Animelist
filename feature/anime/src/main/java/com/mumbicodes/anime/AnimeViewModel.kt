package com.mumbicodes.anime

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mumbicodes.anime.constants.ANIMEID
import com.mumbicodes.common.result.Result
import com.mumbicodes.domain.repository.AnimeRepository
import com.mumbicodes.domain.repository.TrailerRepository
import com.mumbicodes.model.data.Anime
import com.mumbicodes.ui.controller.PlayerController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val animeRepository: AnimeRepository,
    private val trailerRepository: TrailerRepository,
    private val playerController: PlayerController,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _animeDetails: MutableStateFlow<AnimeDetailsScreenState> = MutableStateFlow(
        AnimeDetailsScreenState(player = playerController.controllerPlayer)
    )
    val animeDetails: StateFlow<AnimeDetailsScreenState> = _animeDetails.asStateFlow()

    init {
        observeAnimeDetails()
        updatePlayerState()
    }

    private fun observeAnimeDetails() {
        viewModelScope.launch {
            getAnime(
                animeId = savedStateHandle.get<Int>(ANIMEID) ?: 0
            ).collectLatest { animeDetailsResult ->
                _animeDetails.value = animeDetails.value.copy(
                    animeDetails = when (animeDetailsResult) {
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
                                animeDetails = animeDetailsResult.data.copy(
                                    recommendations = animeDetailsResult.data
                                        .recommendations?.let { recommendedAnimes ->
                                            recommendedAnimes.filterNot {
                                                it?.id == animeDetailsResult.data.id
                                            }
                                        }
                                )
                            )
                        }
                    }
                )
            }
        }
    }

    private fun updatePlayerState() {
        viewModelScope.launch {
            playerController.playerState.collectLatest { playerState ->
                _animeDetails.value = animeDetails.value.copy(
                    playerControllerState = playerState
                )
            }
        }
    }

    private fun getAnime(animeId: Int): Flow<Result<Anime>> {
        return animeRepository.getAnime(
            animeId = animeId,
            page = 0,
            perPage = 10
        )
    }
}