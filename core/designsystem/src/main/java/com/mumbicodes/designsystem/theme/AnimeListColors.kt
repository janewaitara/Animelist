package com.mumbicodes.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class AnimeListColors(
    val background: Color,
    val cardColors: Color,
    val primary: Color,
    val onPrimary: Color,
    val surfacePrimaryHover: Color,
    val surfacePrimaryDisabled: Color,
    val surfacePrimaryBorder: Color,
    val textStrong: Color,
    val textNeutral: Color,
    val textWeak: Color,
    val error: Color,
    val onError: Color,
    val darkErrorText: Color,
    val surfaceBorderError: Color,
    val success: Color,
    val onSuccess: Color,
    val darkSuccessText: Color,
    val surfaceBorderSuccess: Color,
    val info: Color,
    val onInfo: Color,
    val darkInfoText: Color,
    val surfaceBorderInfo: Color
)

fun lightAnimeListColors(
    background: Color = Background_light,
    cardColors: Color = Background_white,
    primary: Color = Pumpkin_400,
    onPrimary: Color = Pumpkin_100,
    surfacePrimaryHover: Color = Pumpkin_500,
    surfacePrimaryDisabled: Color = Pumpkin_200,
    surfacePrimaryBorder: Color = Pumpkin_200,
    textStrong: Color = Grey_800,
    textNeutral: Color = Grey_700,
    textWeak: Color = Grey_500,
    error: Color = FireEngine_400,
    onError: Color = FireEngine_100,
    darkErrorText: Color = FireEngine_500,
    surfaceBorderError: Color = FireEngine_200,
    success: Color = DarkPastel_400,
    onSuccess: Color = DarkPastel_100,
    darkSuccessText: Color = DarkPastel_600,
    surfaceBorderSuccess: Color = DarkPastel_300,
    info: Color = Azure_400,
    onInfo: Color = Azure_100,
    darkInfoText: Color = Azure_600,
    surfaceBorderInfo: Color = Azure_300
) = AnimeListColors(
    background = background,
    cardColors = cardColors,
    primary = primary,
    onPrimary = onPrimary,
    surfacePrimaryHover = surfacePrimaryHover,
    surfacePrimaryDisabled = surfacePrimaryDisabled,
    surfacePrimaryBorder = surfacePrimaryBorder,
    textStrong = textStrong,
    textNeutral = textNeutral,
    textWeak = textWeak,
    error = error,
    onError = onError,
    darkErrorText = darkErrorText,
    surfaceBorderError = surfaceBorderError,
    success = success,
    onSuccess = onSuccess,
    darkSuccessText = darkSuccessText,
    surfaceBorderSuccess = surfaceBorderSuccess,
    info = info,
    onInfo = onInfo,
    darkInfoText = darkInfoText,
    surfaceBorderInfo = surfaceBorderInfo

)

fun darkAnimeListColors(
    background: Color = Background_dark,
    cardColors: Color = Background_dark_grey,
    primary: Color = Pumpkin_300,
    onPrimary: Color = Pumpkin_700,
    surfacePrimaryHover: Color = Pumpkin_200,
    surfacePrimaryDisabled: Color = Pumpkin_600,
    surfacePrimaryBorder: Color = Pumpkin_600,
    textStrong: Color = Grey_100,
    textNeutral: Color = Grey_200,
    textWeak: Color = Grey_400,
    error: Color = FireEngine_300,
    onError: Color = FireEngine_700,
    darkErrorText: Color = FireEngine_200,
    surfaceBorderError: Color = FireEngine_300,
    success: Color = DarkPastel_300,
    onSuccess: Color = DarkPastel_700,
    darkSuccessText: Color = DarkPastel_200,
    surfaceBorderSuccess: Color = DarkPastel_300,
    info: Color = Azure_300,
    onInfo: Color = Azure_700,
    darkInfoText: Color = Azure_200,
    surfaceBorderInfo: Color = Azure_300
) = AnimeListColors(
    background = background,
    cardColors = cardColors,
    primary = primary,
    onPrimary = onPrimary,
    surfacePrimaryHover = surfacePrimaryHover,
    surfacePrimaryDisabled = surfacePrimaryDisabled,
    surfacePrimaryBorder = surfacePrimaryBorder,
    textStrong = textStrong,
    textNeutral = textNeutral,
    textWeak = textWeak,
    error = error,
    onError = onError,
    darkErrorText = darkErrorText,
    surfaceBorderError = surfaceBorderError,
    success = success,
    onSuccess = onSuccess,
    darkSuccessText = darkSuccessText,
    surfaceBorderSuccess = surfaceBorderSuccess,
    info = info,
    onInfo = onInfo,
    darkInfoText = darkInfoText,
    surfaceBorderInfo = surfaceBorderInfo

)

val LocalAnimeListColors = staticCompositionLocalOf<AnimeListColors> {
    lightAnimeListColors()
}

// Pumpkin Orange
val Pumpkin_700 = Color(0xFF492100)
val Pumpkin_600 = Color(0xFF924200)
val Pumpkin_500 = Color(0xFFC85A00)
val Pumpkin_400 = Color(0xFFFF7300)
val Pumpkin_300 = Color(0xFFFF9B49)
val Pumpkin_200 = Color(0xFFFFCDA4)
val Pumpkin_100 = Color(0xFFFFF5ED)

// State colors: DarkPastel Green
val DarkPastel_700 = Color(0xFF014415)
val DarkPastel_600 = Color(0xFF026D22)
val DarkPastel_500 = Color(0xFF02962F)
val DarkPastel_400 = Color(0xFF03C03C)
val DarkPastel_300 = Color(0xFF4DFC82)
val DarkPastel_200 = Color(0xFFA6FEC0)
val DarkPastel_100 = Color(0xFFE9FFEF)

// State colors: Azure Blue
val Azure_700 = Color(0xFF002E5B)
val Azure_600 = Color(0xFF004992)
val Azure_500 = Color(0xFF006DDB)
val Azure_400 = Color(0xFF007FFF)
val Azure_300 = Color(0xFF49A4FF)
val Azure_200 = Color(0xFFA4D1FF)
val Azure_100 = Color(0xFFEDF6FF)

// State colors: FireEngine Red
val FireEngine_700 = Color(0xFF5B0B0F)
val FireEngine_600 = Color(0xFF891117)
val FireEngine_500 = Color(0xFFB7171F)
val FireEngine_400 = Color(0xFFD71B24)
val FireEngine_300 = Color(0xFFEA535B)
val FireEngine_200 = Color(0xFFF2989C)
val FireEngine_100 = Color(0xFFFBDDDE)

// Grey scale
val Grey_800 = Color(0xFF060315)
val Grey_700 = Color(0xFF302D3C)
val Grey_600 = Color(0xFF555166)
val Grey_500 = Color(0xFF7C798E)
val Grey_400 = Color(0xFFBFBCCD)
val Grey_300 = Color(0xFFD7D7E5)
val Grey_200 = Color(0xFFEDEBF3)
val Grey_100 = Color(0xFFF4F2FC)

// Backgrounds

val Background_dark = Color(0xFF111111)
val Background_light = Color(0xFFFBFBFD)
val Background_white = Color(0xFFFFFFFF)
val Background_dark_grey = Color(0xFF222129)