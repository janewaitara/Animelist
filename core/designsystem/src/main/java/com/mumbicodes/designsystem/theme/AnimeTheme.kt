package com.mumbicodes.designsystem.theme

import androidx.compose.runtime.Composable

/**
 * Contains functions to access the current theme values
 * provided at the call site's position in the hierarchy.
 * */
object AnimeTheme {
    val colors: AnimeListColors
        @Composable
        get() = LocalAnimeListColors.current

    val typography: AnimeListTypography
        @Composable
        get() = LocalAnimeListTypography.current

    val shapes: AnimeListShapes
        @Composable
        get() = LocalAnimeListShapes.current

    val space: AnimeListSpacing
        @Composable
        get() = LocalAnimeListSpace.current
}