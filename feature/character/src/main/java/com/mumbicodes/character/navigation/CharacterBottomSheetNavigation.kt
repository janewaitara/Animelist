package com.mumbicodes.character.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet

const val CHARACTER_BOTTOM_SHEET_ROUTE = "character_route"

fun NavController.navigateToCharacterBottomSheet(navOptions: NavOptions? = null) {
    this.navigate(CHARACTER_BOTTOM_SHEET_ROUTE, navOptions)
}

@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.characterBottomSheet() {
    bottomSheet(route = CHARACTER_BOTTOM_SHEET_ROUTE) {
        Box(modifier = Modifier.size(100.dp)) {
            Text("This is a cool bottom sheet!")
        }
    }
}