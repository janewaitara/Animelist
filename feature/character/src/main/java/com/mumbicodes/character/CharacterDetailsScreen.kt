package com.mumbicodes.character

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CharacterDetailsScreenRoute(
    modifier: Modifier = Modifier,
    characterViewModel: CharacterViewModel = hiltViewModel()
) {
    val characterDetails by characterViewModel.characterUiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.size(100.dp)) {
        Text("This is a cool bottom sheet!")
        when (characterDetails) {
            is CharacterScreenUiState.CharacterDetails -> {
                Text((characterDetails as CharacterScreenUiState.CharacterDetails).data.name?.full ?: "")
            }
            is CharacterScreenUiState.Error -> {
            }
            CharacterScreenUiState.Loading -> {
                Text("Loading")
            }
        }
    }
}