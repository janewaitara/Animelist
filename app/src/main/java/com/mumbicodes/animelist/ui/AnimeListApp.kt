package com.mumbicodes.animelist.com.mumbicodes.animelist.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mumbicodes.animelist.com.mumbicodes.animelist.navigation.AnimeListNavHost
import com.mumbicodes.animelist.com.mumbicodes.animelist.navigation.BottomBarComponent
import com.mumbicodes.animelist.com.mumbicodes.animelist.navigation.mainAppDestinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeListApp(
    animeListAppState: AnimeListAppState = rememberAnimeListAppState()
) {
    Scaffold(
        bottomBar = {
            if (animeListAppState.shouldShowBottomBarOnScreen) {
                BottomBarComponent(
                    destinations = mainAppDestinations,
                    currentDestination = animeListAppState.currentDestination,
                    onItemClick = animeListAppState::navigateToMainAppDestinations
                )
            }
        }
    ) { paddingValues ->
        AnimeListNavHost(
            modifier = Modifier.padding(paddingValues),
            animeListAppState = animeListAppState
        )
    }
}