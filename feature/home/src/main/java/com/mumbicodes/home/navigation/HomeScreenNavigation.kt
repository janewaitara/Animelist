package com.mumbicodes.home.navigation

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
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.mumbicodes.home.HomeScreenRoute
import com.mumbicodes.home.HomeScreenViewModel

const val HOME_GRAPH = "home_graph"
const val HOME_SCREEN_ROUTE = "home_route"
const val ALL_CATEGORIES_ROUTE = "all_categories"

fun NavController.navigateToHomeGraph(navOptions: NavOptions? = null) {
    this.navigate(HOME_GRAPH, navOptions)
}

fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    onAnimeClicked: () -> Unit,
    onSeeAllButtonClicked: () -> Unit
) {
    navigation(
        startDestination = HOME_SCREEN_ROUTE,
        route = HOME_GRAPH
    ) {
        composable(route = HOME_SCREEN_ROUTE) {
            HomeScreenRoute(
                modifier = Modifier,
                onAnimeClicked = onAnimeClicked,
                homeScreenViewModel = it.sharedViewModel<HomeScreenViewModel>(navController = navController),
                onSeeAllButtonClicked = onSeeAllButtonClicked
            )
        }
        composable(route = ALL_CATEGORIES_ROUTE) {
            AllCategoriesScreenRoute(
                onAnimeClicked = onAnimeClicked,
                homeScreenViewModel = it.sharedViewModel<HomeScreenViewModel>(navController = navController)
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