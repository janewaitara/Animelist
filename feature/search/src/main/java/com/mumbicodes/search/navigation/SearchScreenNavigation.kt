package com.mumbicodes.search.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mumbicodes.search.SearchScreen

const val SEARCH_SCREEN_ROUTE = "search_route"

fun NavController.navigateToSearchScreen(navOptions: NavOptions? = null) {
    this.navigate(route = SEARCH_SCREEN_ROUTE, navOptions = navOptions)
}

fun NavGraphBuilder.searchScreen(
    onAnimeClicked: (Int) -> Unit,
    onCharacterClicked: (Int) -> Unit
) {
    composable(route = SEARCH_SCREEN_ROUTE) {
        SearchScreen(
            modifier = Modifier,
            onAnimeClicked = onAnimeClicked,
            onCharacterClicked = onCharacterClicked
        )
    }
}