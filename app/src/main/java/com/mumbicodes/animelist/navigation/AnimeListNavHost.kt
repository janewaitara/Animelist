package com.mumbicodes.animelist.com.mumbicodes.animelist.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.mumbicodes.anime.navigation.animeDetailsScreen
import com.mumbicodes.anime.navigation.navigateToAnimeDetails
import com.mumbicodes.animelist.com.mumbicodes.animelist.ui.AnimeListAppState
import com.mumbicodes.character.navigation.characterBottomSheet
import com.mumbicodes.home.navigation.HOME_SCREEN_ROUTE
import com.mumbicodes.home.navigation.homeScreen
import com.mumbicodes.search.navigation.searchScreen
import com.mumbicodes.yourlist.navigation.yourListScreen

@Composable
fun AnimeListNavHost(
    modifier: Modifier = Modifier,
    animeListAppState: AnimeListAppState,
    startDestination: String = HOME_SCREEN_ROUTE
) {
    val navController = animeListAppState.navController
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        homeScreen(
            onAnimeClicked = { navController.navigateToAnimeDetails() }
        )
        searchScreen()
        yourListScreen()
        animeDetailsScreen()
        characterBottomSheet()
    }
}