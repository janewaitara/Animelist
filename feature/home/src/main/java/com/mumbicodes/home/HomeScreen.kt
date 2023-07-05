package com.mumbicodes.home

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(modifier: Modifier = Modifier, onAnimeClicked: () -> Unit) {
    Text(
        text = "This is the home screen",
        modifier = modifier.clickable {
            onAnimeClicked()
        }
    )
}