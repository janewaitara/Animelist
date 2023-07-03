package com.mumbicodes.anime.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mumbicodes.anime.AnimeDetailsScreen

const val ANIME_DETAILS_ROUTE = "anime_details_route"

fun NavController.navigateToAnimeDetails(navOptions: NavOptions? = null) {
    this.navigate(ANIME_DETAILS_ROUTE, navOptions)
}

fun NavGraphBuilder.animeDetailsScreen() {
    composable(route = ANIME_DETAILS_ROUTE) {
        AnimeDetailsScreen(modifier = Modifier)
    }
}