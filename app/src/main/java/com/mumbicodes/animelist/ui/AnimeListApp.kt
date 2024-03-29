package com.mumbicodes.animelist.com.mumbicodes.animelist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.mumbicodes.animelist.com.mumbicodes.animelist.navigation.AnimeListNavHost
import com.mumbicodes.animelist.com.mumbicodes.animelist.navigation.BottomBarComponent
import com.mumbicodes.animelist.com.mumbicodes.animelist.navigation.mainAppDestinations
import com.mumbicodes.designsystem.theme.AnimeTheme

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalLayoutApi::class)
@Composable
fun AnimeListApp(
    animeListAppState: AnimeListAppState = rememberAnimeListAppState()
) {
    /***
     *  With this, The bottom bar appears to be slightly transparent based on the designs and the content behind it seen
     */

    Column {
        Spacer(
            Modifier.windowInsetsTopHeight(
                WindowInsets.systemBars
            )
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .background(AnimeTheme.colors.background)
        ) {
            AnimeListNavHost(
                animeListAppState = animeListAppState
            )

            if (animeListAppState.shouldShowBottomBarOnScreen) {
                BottomBarComponent(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    destinations = mainAppDestinations,
                    currentDestination = animeListAppState.currentDestination,
                    onItemClick = animeListAppState::navigateToMainAppDestinations
                )
            }
        }

        Spacer(
            Modifier
                .windowInsetsBottomHeight(
                    WindowInsets.systemBars
                )
        )
    }
}
/***
 * With this, The bottom bar transparency is not seen because of the scaffold container color. Also, you can't see the content behind.
 */
/*Scaffold(
    containerColor = AnimeTheme.colors.background,
    bottomBar = {
        if (animeListAppState.shouldShowBottomBarOnScreen) {
            BottomBarComponent(
                destinations = mainAppDestinations,
                currentDestination = animeListAppState.currentDestination,
                onItemClick = animeListAppState::navigateToMainAppDestinations
            )
        }
    }
) { paddingValues ->
    AnimeListNavHost(
        modifier = Modifier.padding(paddingValues),
        animeListAppState = animeListAppState
    )
}*/