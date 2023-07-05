package com.mumbicodes.home

import androidx.lifecycle.ViewModel
import com.mumbicodes.domain.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor
(private val animeRepository: AnimeRepository) : ViewModel() {

    private val _uiState =
        MutableStateFlow(HomeScreenUiStates.Loading)

    val uiState: StateFlow<HomeScreenUiStates> = _uiState.asStateFlow()
}