package com.mumbicodes.home.navigation

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mumbicodes.designsystem.atoms.Text

@Composable
fun AllCategoriesScreenRoute(
    onAnimeClicked: () -> Unit
) {
    Text(
        text = "This is the categories screen",
        modifier = Modifier.clickable {
            onAnimeClicked()
        }
    )
}