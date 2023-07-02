package com.mumbicodes.animelist.com.mumbicodes.animelist.navigation

import androidx.annotation.DrawableRes
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.mumbicodes.designsystem.icons.AnimeListIcons
import com.mumbicodes.home.R as home
import com.mumbicodes.search.R as search
import com.mumbicodes.yourlist.R as yourList

enum class MainAppDestinations(
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    val iconLabel: Int,
    val titleTextId: Int
) {
    HOME(
        selectedIcon = AnimeListIcons.homeFilled,
        unselectedIcon = AnimeListIcons.homeFilled,
        iconLabel = home.string.home,
        titleTextId = home.string.home
    ),
    SEARCH(
        selectedIcon = AnimeListIcons.homeFilled,
        unselectedIcon = AnimeListIcons.homeFilled,
        iconLabel = search.string.search,
        titleTextId = search.string.search
    ),
    YOUR_LIST(
        selectedIcon = AnimeListIcons.homeFilled,
        unselectedIcon = AnimeListIcons.homeFilled,
        iconLabel = yourList.string.yourList,
        titleTextId = yourList.string.yourList
    )
}

val mainAppDestinations = MainAppDestinations.values().asList()

fun NavDestination?.isSelectedDestinationInHierarchy(destination: MainAppDestinations) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false