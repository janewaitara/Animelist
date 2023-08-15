package com.mumbicodes.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.mumbicodes.designsystem.R

val outFitFont = FontFamily(
    Font(R.font.outfit_medium, FontWeight.Medium),
    Font(R.font.outfit_light, FontWeight.Light)
)

@Immutable
data class AnimeListTypography(
    val titleLarge: TextStyle = TextStyle(
        fontFamily = outFitFont,
        fontWeight = FontWeight.Medium,
        fontSize = 36.sp,
        lineHeight = 52.sp,
        letterSpacing = (-0.5).em
    ),
    val titleMedium: TextStyle = TextStyle(
        fontFamily = outFitFont,
        fontWeight = FontWeight.Medium,
        fontSize = 32.sp,
        lineHeight = 44.sp
    ),
    val titleSmall: TextStyle = TextStyle(
        fontFamily = outFitFont,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    val subTitle: TextStyle = TextStyle(
        fontFamily = outFitFont,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = (-0.01).em
    ),
    val bodyLargeBold: TextStyle = TextStyle(
        fontFamily = outFitFont,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),
    val bodyMediumBold: TextStyle = TextStyle(
        fontFamily = outFitFont,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    val bodyMedium: TextStyle = TextStyle(
        fontFamily = outFitFont,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    val bodySmall: TextStyle = TextStyle(
        fontFamily = outFitFont,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    val bodyExtraSmall: TextStyle = TextStyle(
        fontFamily = outFitFont,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
        lineHeight = 16.sp
    )
)

val LocalAnimeListTypography = staticCompositionLocalOf {
    AnimeListTypography()
}