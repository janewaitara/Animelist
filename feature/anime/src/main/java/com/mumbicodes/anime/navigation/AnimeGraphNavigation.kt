package com.mumbicodes.anime.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.mumbicodes.anime.AllCharactersRoute
import com.mumbicodes.anime.AnimeDetailsRoute
import com.mumbicodes.anime.constants.ANIMEID

const val ANIME_GRAPH = "anime_graph_route"
const val ANIME_DETAILS_ROUTE = "anime_details_route"
const val ALL_CHARACTERS_ROUTE = "all_characters_route"

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
fun NavGraphBuilder.animeDetailsGraph(
    navController: NavHostController,
    onCharacterClicked: (Int) -> Unit,
    onCharactersSeeAllClicked: () -> Unit
) {
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
            AnimeDetailsRoute(
                modifier = Modifier,
                onCharacterClicked = onCharacterClicked,
                animeViewModel = it.sharedViewModel(navController = navController),
                onAnimeClicked = {},
                onCharactersSeeAllClicked = onCharactersSeeAllClicked,
                onBackButtonClicked = { navController.navigateUp() }
            )
        }
        composable(
            route = ALL_CHARACTERS_ROUTE
        ) {
            AllCharactersRoute(
                modifier = Modifier,
                onCharacterClicked = onCharacterClicked,
                animeDetailsViewModel = it.sharedViewModel(navController = navController),
                onBackButtonClicked = { navController.navigateUp() }
            )
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}