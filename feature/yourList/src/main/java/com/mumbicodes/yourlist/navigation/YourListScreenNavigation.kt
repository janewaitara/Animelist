package com.mumbicodes.yourlist.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mumbicodes.yourlist.YourListScreen

const val YOUR_LIST_SCREEN_ROUTE = "your_list_route"

fun NavController.navigateToYourListScreen(navOptions: NavOptions? = null) {
    this.navigate(route = YOUR_LIST_SCREEN_ROUTE, navOptions = navOptions)
}

fun NavGraphBuilder.yourListScreen() {
    composable(route = YOUR_LIST_SCREEN_ROUTE) {
        YourListScreen(modifier = Modifier)
    }
}