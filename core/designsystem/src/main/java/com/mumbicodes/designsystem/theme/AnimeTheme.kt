package com.mumbicodes.designsystem.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

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

@Composable
fun AnimeTheme(
    colors: AnimeListColors = AnimeTheme.colors,
    typography: AnimeListTypography = AnimeTheme.typography,
    shapes: AnimeListShapes = AnimeTheme.shapes,
    spaces: AnimeListSpacing = AnimeTheme.space,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalAnimeListColors provides colors,
        LocalAnimeListTypography provides typography,
        LocalAnimeListShapes provides shapes,
        LocalAnimeListSpace provides spaces
    ) {
        content()
    }
}

@Composable
fun AnimeListTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkAnimeListColors()
        else -> lightAnimeListColors()
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    AnimeTheme(
        colors = colorScheme,
        content = content
    )
}