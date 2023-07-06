package com.mumbicodes.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mumbicodes.common.result.Result
import com.mumbicodes.domain.repository.AnimeRepository
import com.mumbicodes.network.RecommendationsQuery
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

    private var _uiState: StateFlow<HomeScreenUiStates> =
        recommendedAnimes.map {
            when (it) {
                is Result.ApplicationError -> {
                    HomeScreenUiStates.Error(it.errors.joinToString())
                }

                is Result.Failure -> {
                    HomeScreenUiStates.Error(it.toString())
                }

                Result.Loading -> {
                    Log.e("Data", "KLoaing")
                    HomeScreenUiStates.Loading
                }

                is Result.Success -> {
                    Log.e("Data 6", it.data.first().media?.title?.english ?: "No data")
                    HomeScreenUiStates.Animes(recommended = it.data)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeScreenUiStates.Loading
        )

    val uiState: StateFlow<HomeScreenUiStates> = _uiState
}