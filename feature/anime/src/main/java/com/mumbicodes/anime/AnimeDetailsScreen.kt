package com.mumbicodes.anime

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AnimeDetailsScreen(
    modifier: Modifier = Modifier,
    animeViewModel: AnimeViewModel = hiltViewModel(),
    onCharacterClicked: () -> Unit
) {
    val animeDetailsState: AnimeDetailsScreenUiState
        by animeViewModel.animeDetails.collectAsStateWithLifecycle()

    when (animeDetailsState) {
        is AnimeDetailsScreenUiState.AnimeDetails -> {
            Text(
                text = "This is the anime name: " +
                    "${(animeDetailsState as AnimeDetailsScreenUiState.AnimeDetails).animeDetails.title}",
                modifier = modifier.clickable {
                    onCharacterClicked()
                }
            )
        }

        is AnimeDetailsScreenUiState.Error -> {
            TODO()
        }

        is AnimeDetailsScreenUiState.Loading -> {
            Text(
                text = "Loading",
                modifier = modifier.clickable {
                    onCharacterClicked()
                }
            )
        }
    }
}