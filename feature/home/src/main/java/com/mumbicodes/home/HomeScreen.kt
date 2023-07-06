package com.mumbicodes.home

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onAnimeClicked: () -> Unit,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {
    val homeScreenUiStates: HomeScreenUiStates by homeScreenViewModel.uiState.collectAsStateWithLifecycle()
    when (homeScreenUiStates) {
        is HomeScreenUiStates.Animes -> {
            Text(
                text = (homeScreenUiStates as HomeScreenUiStates.Animes).recommended.first().media?.title?.english
                    ?: "It is empty",
                modifier = modifier.clickable {
                    onAnimeClicked()
                }
            )
        }

        is HomeScreenUiStates.Error -> {
            TODO()
        }

        HomeScreenUiStates.Loading -> {
            Text(
                text = "This is the loading state",
                modifier = modifier.clickable {
                    onAnimeClicked()
                }
            )
        }
    }
}