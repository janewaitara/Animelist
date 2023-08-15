package com.mumbicodes.designsystem.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

data class AnimeListShapes(
    val smallShape: CornerBasedShape = RoundedCornerShape(4.dp),
    val mediumShape: CornerBasedShape = RoundedCornerShape(8.dp),
    val largeShape: CornerBasedShape = RoundedCornerShape(36.dp)
)

val LocalAnimeListShapes = staticCompositionLocalOf {
    AnimeListShapes()
}