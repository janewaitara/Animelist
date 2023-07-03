package com.mumbicodes.home.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mumbicodes.home.HomeScreen

const val HOME_SCREEN_ROUTE = "home_route"

fun NavController.navigateToHomeScreen(navOptions: NavOptions? = null) {
    this.navigate(HOME_SCREEN_ROUTE, navOptions)
}

fun NavGraphBuilder.homeScreen() {
    composable(route = HOME_SCREEN_ROUTE) {
        HomeScreen(modifier = Modifier)
    }
}