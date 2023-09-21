package com.mumbicodes.anime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AllCharactersRoute(
    modifier: Modifier = Modifier,
    animeDetailsViewModel: AnimeViewModel = hiltViewModel(),
    onAnimeClicked: (Int) -> Unit = {},
    onBackButtonClicked: () -> Unit = {}
) {
    val animeDetailsUiState by animeDetailsViewModel.animeDetails.collectAsStateWithLifecycle()

    AllCharactersScreen(
        modifier = modifier,
        animeDetails = animeDetailsUiState,
        onAnimeClicked = onAnimeClicked,
        onBackButtonClicked = onBackButtonClicked
    )
}

@Composable
fun AllCharactersScreen(
    modifier: Modifier = Modifier,
    animeDetails: AnimeDetailsScreenUiState,
    onAnimeClicked: (Int) -> Unit = {},
    onBackButtonClicked: () -> Unit = {}
) {
    when (animeDetails) {
        is AnimeDetailsScreenUiState.AnimeDetails -> {
        }
        is AnimeDetailsScreenUiState.Error -> TODO()
        is AnimeDetailsScreenUiState.Loading -> TODO()
    }
}