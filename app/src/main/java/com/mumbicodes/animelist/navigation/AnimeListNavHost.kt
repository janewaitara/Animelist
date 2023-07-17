package com.mumbicodes.animelist.com.mumbicodes.animelist.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.mumbicodes.anime.navigation.animeDetailsScreen
import com.mumbicodes.anime.navigation.navigateToAnimeDetails
import com.mumbicodes.animelist.com.mumbicodes.animelist.ui.AnimeListAppState
import com.mumbicodes.character.navigation.characterBottomSheet
import com.mumbicodes.character.navigation.navigateToCharacterBottomSheet
import com.mumbicodes.home.navigation.HOME_SCREEN_ROUTE
import com.mumbicodes.home.navigation.homeScreen
import com.mumbicodes.search.navigation.searchScreen
import com.mumbicodes.yourlist.navigation.yourListScreen

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun AnimeListNavHost(
    modifier: Modifier = Modifier,
    animeListAppState: AnimeListAppState,
    startDestination: String = HOME_SCREEN_ROUTE
) {
    val navController = animeListAppState.navController

    ModalBottomSheetLayout(animeListAppState.bottomSheetNavigator) {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = startDestination
        ) {
            homeScreen(
                onAnimeClicked = {
                    navController.navigateToAnimeDetails()
                }
            )
            searchScreen()
            yourListScreen()
            animeDetailsScreen(
                onCharacterClicked = {
                    navController.navigateToCharacterBottomSheet()
                }
            )
            characterBottomSheet()
        }
    }
}