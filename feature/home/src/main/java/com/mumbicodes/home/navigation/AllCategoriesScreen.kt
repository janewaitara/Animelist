package com.mumbicodes.home.navigation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mumbicodes.designsystem.atoms.Text
import com.mumbicodes.home.AnimeSortType
import com.mumbicodes.home.HomeScreenViewModel

@Composable
fun AllCategoriesScreenRoute(
    modifier: Modifier = Modifier,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    onAnimeClicked: () -> Unit
) {
    val animeSortType by homeScreenViewModel.animeSortType.collectAsStateWithLifecycle()

    when (animeSortType) {
        AnimeSortType.RECOMMENDED -> Log.d("ANimes", "Recommended animes selected")
        AnimeSortType.TRENDING -> Log.d("Animes", "Trending animes have been selected")
        AnimeSortType.POPULAR -> Log.d("ANimes", "Popular animes selected")
    }

    Text(
        text = "This is the categories screen",
        modifier = Modifier.clickable {
            onAnimeClicked()
        }
    )
}