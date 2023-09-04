package com.mumbicodes.anime.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.mumbicodes.anime.AnimeDetailsRoute
import com.mumbicodes.anime.constants.ANIMEID

const val ANIME_GRAPH = "anime_graph_route"
const val ANIME_DETAILS_ROUTE = "anime_details_route"

/**
 * Ideally, With nested navigation, we supposed to navigate to the graph first them the composables
 * But when a composable has args, there is no guidelines on how to pass them to composable/ViewModel
 * from the nested Nav*/
fun NavController.navigateToAnimeGraph(navOptions: NavOptions? = null) {
    this.navigate(ANIME_GRAPH, navOptions)
}
fun NavController.navigateToAnimeScreen(animeId: Int, navOptions: NavOptions? = null) {
    this.navigate("$ANIME_DETAILS_ROUTE/$animeId", navOptions)
}

/**
 * TODO add nested navigation for anime characters
 * */
fun NavGraphBuilder.animeDetailsScreen(onCharacterClicked: () -> Unit) {
    navigation(
        startDestination = ANIME_DETAILS_ROUTE,
        route = ANIME_GRAPH
    ) {
        composable(
            route = "$ANIME_DETAILS_ROUTE/{$ANIMEID}",
            arguments = listOf(
                navArgument(
                    name = ANIMEID
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AnimeDetailsRoute(modifier = Modifier, onCharacterClicked = onCharacterClicked)
        }
    }
}