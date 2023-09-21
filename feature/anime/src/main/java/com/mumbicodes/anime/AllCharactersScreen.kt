package com.mumbicodes.anime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mumbicodes.designsystem.components.ListLoadingComponent

@Composable
fun AllCharactersRoute(
    modifier: Modifier = Modifier,
    animeDetailsViewModel: AnimeViewModel = hiltViewModel(),
    onCharacterClicked: (Int) -> Unit = {},
    onBackButtonClicked: () -> Unit = {}
) {
    val animeDetailsUiState by animeDetailsViewModel.animeDetails.collectAsStateWithLifecycle()

    AllCharactersScreen(
        modifier = modifier,
        animeDetails = animeDetailsUiState,
        onCharacterClicked = onCharacterClicked,
        onBackButtonClicked = onBackButtonClicked
    )
}

@Composable
fun AllCharactersScreen(
    modifier: Modifier = Modifier,
    animeDetails: AnimeDetailsScreenUiState,
    onCharacterClicked: (Int) -> Unit = {},
    onBackButtonClicked: () -> Unit = {}
) {
    when (animeDetails) {
        is AnimeDetailsScreenUiState.AnimeDetails -> {
        }
        is AnimeDetailsScreenUiState.Error -> TODO()
        is AnimeDetailsScreenUiState.Loading -> {
            ListLoadingComponent()
        }
    }
}