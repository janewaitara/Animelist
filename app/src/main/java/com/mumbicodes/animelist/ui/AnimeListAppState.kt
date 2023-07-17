package com.mumbicodes.animelist.com.mumbicodes.animelist.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.mumbicodes.animelist.com.mumbicodes.animelist.navigation.MainAppDestinations
import com.mumbicodes.animelist.com.mumbicodes.animelist.navigation.MainAppDestinations.HOME
import com.mumbicodes.animelist.com.mumbicodes.animelist.navigation.MainAppDestinations.SEARCH
import com.mumbicodes.animelist.com.mumbicodes.animelist.navigation.MainAppDestinations.YOUR_LIST
import com.mumbicodes.home.navigation.HOME_SCREEN_ROUTE
import com.mumbicodes.home.navigation.navigateToHomeScreen
import com.mumbicodes.search.navigation.SEARCH_SCREEN_ROUTE
import com.mumbicodes.search.navigation.navigateToSearchScreen
import com.mumbicodes.yourlist.navigation.YOUR_LIST_SCREEN_ROUTE
import com.mumbicodes.yourlist.navigation.navigateToYourListScreen

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun rememberAnimeListAppState(
    bottomSheetNavigator: BottomSheetNavigator = rememberBottomSheetNavigator(),
    navController: NavHostController = rememberNavController(
        bottomSheetNavigator
    )
): AnimeListAppState {
    return remember(navController, bottomSheetNavigator) {
        AnimeListAppState(navController, bottomSheetNavigator)
    }
}

@Stable
@OptIn(ExperimentalMaterialNavigationApi::class)
class AnimeListAppState(
    val navController: NavHostController,
    val bottomSheetNavigator: BottomSheetNavigator
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val shouldShowBottomBarOnScreen: Boolean
        @Composable get() = currentDestination?.route == HOME_SCREEN_ROUTE ||
            currentDestination?.route == SEARCH_SCREEN_ROUTE ||
            currentDestination?.route == YOUR_LIST_SCREEN_ROUTE

    fun navigateToMainAppDestinations(mainAppDestinations: MainAppDestinations) {
        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reSelecting the same item
            launchSingleTop = true
            // Restore state when reSelecting a previously selected item
            restoreState = true
        }

        when (mainAppDestinations) {
            HOME -> navController.navigateToHomeScreen(topLevelNavOptions)
            SEARCH -> navController.navigateToSearchScreen(topLevelNavOptions)
            YOUR_LIST -> navController.navigateToYourListScreen(topLevelNavOptions)
        }
    }
}