package com.mumbicodes.designsystem.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Work on the space tokens for the different form factors
 * */
data class AnimeListSpacing(
    val space4dp: Dp = 4.dp,
    val space8dp: Dp = 8.dp,
    val space16dp: Dp = 16.dp,
    val space12dp: Dp = 12.dp,
    val space20dp: Dp = 20.dp,
    val space24dp: Dp = 24.dp,
    val space32dp: Dp = 32.dp,
    val space36dp: Dp = 36.dp,
    val space48dp: Dp = 48.dp
)

val LocalAnimeListSpace = staticCompositionLocalOf {
    AnimeListSpacing()
}