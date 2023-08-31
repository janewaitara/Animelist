package com.mumbicodes.anime.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mumbicodes.anime.AnimeDetailsScreen

const val ANIME_GRAPH = "anime_graph_route"
const val ANIME_DETAILS_ROUTE = "anime_details_route"

fun NavController.navigateToAnimeGraph(navOptions: NavOptions? = null) {
    this.navigate(ANIME_GRAPH, navOptions)
}

/**
 * TODO add nested navigation for anime characters
 * */
fun NavGraphBuilder.animeDetailsScreen(onCharacterClicked: () -> Unit) {
    navigation(
        startDestination = ANIME_DETAILS_ROUTE,
        route = ANIME_GRAPH
    ) {
        composable(route = ANIME_DETAILS_ROUTE) {
            AnimeDetailsScreen(modifier = Modifier, onCharacterClicked = onCharacterClicked)
        }
    }
}