package com.mumbicodes.anime

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AnimeDetailsScreen(modifier: Modifier = Modifier, onCharacterClicked: () -> Unit) {
    Text(
        text = "This is the anime screen",
        modifier = modifier.clickable {
            onCharacterClicked()
        }
    )
}