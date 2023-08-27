package com.mumbicodes.home.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.mumbicodes.home.HomeScreenRoute

const val HOME_GRAPH = "home_graph"
const val HOME_SCREEN_ROUTE = "home_route"

fun NavController.navigateToHomeGraph(navOptions: NavOptions? = null) {
    this.navigate(HOME_GRAPH, navOptions)
}

fun NavGraphBuilder.homeGraph(onAnimeClicked: () -> Unit) {
    navigation(
        startDestination = HOME_SCREEN_ROUTE,
        route = HOME_GRAPH
    ) {
        composable(route = HOME_SCREEN_ROUTE) {
            HomeScreenRoute(
                modifier = Modifier,
                onAnimeClicked = onAnimeClicked
            )
        }
    }
}