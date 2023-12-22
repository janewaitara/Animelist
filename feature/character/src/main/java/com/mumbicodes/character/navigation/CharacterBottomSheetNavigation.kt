package com.mumbicodes.character.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.mumbicodes.character.CharacterDetailsScreenRoute
import com.mumbicodes.character.constants.CHARACTERID

const val CHARACTER_BOTTOM_SHEET_ROUTE = "character_route"

fun NavController.navigateToCharacterBottomSheet(navOptions: NavOptions? = null, characterId: Int) {
    this.navigate("$CHARACTER_BOTTOM_SHEET_ROUTE/$characterId", navOptions)
}

@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.characterBottomSheet(
    onAnimeClicked: (Int) -> Unit = {}
) {
    bottomSheet(
        route = "$CHARACTER_BOTTOM_SHEET_ROUTE/{$CHARACTERID}",
        arguments = listOf(
            navArgument(
                name = CHARACTERID
            ) {
                type = NavType.IntType
                defaultValue = -1
            }
        )
    ) {
        CharacterDetailsScreenRoute(
            onAnimeClicked = onAnimeClicked
        )
    }
}