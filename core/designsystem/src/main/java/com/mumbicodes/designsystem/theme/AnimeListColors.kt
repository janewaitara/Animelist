package com.mumbicodes.designsystem.theme

import androidx.compose.runtime.Immutable
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