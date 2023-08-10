package com.mumbicodes.designsystem.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color

@Stable
class AnimeListColors(
    background: Color,
    cardColors: Color,
    primary: Color,
    onPrimary: Color,
    surfacePrimaryHover: Color,
    surfacePrimaryDisabled: Color,
    surfacePrimaryBorder: Color,
    textStrong: Color,
    textNeutral: Color,
    textWeak: Color,
    error: Color,
    onError: Color,
    darkErrorText: Color,
    surfaceBorderError: Color,
    success: Color,
    onSuccess: Color,
    darkSuccessText: Color,
    surfaceBorderSuccess: Color,
    info: Color,
    onInfo: Color,
    darkInfoText: Color,
    surfaceBorderInfo: Color
) {
    var background by mutableStateOf(background, structuralEqualityPolicy())
        internal set
    var cardColors by mutableStateOf(cardColors, structuralEqualityPolicy())
        internal set

    var primary by mutableStateOf(primary, structuralEqualityPolicy())
        internal set
    var onPrimary by mutableStateOf(onPrimary, structuralEqualityPolicy())
        internal set
    var surfacePrimaryHover by mutableStateOf(surfacePrimaryHover, structuralEqualityPolicy())
        internal set
    var surfacePrimaryDisabled by mutableStateOf(surfacePrimaryDisabled, structuralEqualityPolicy())
        internal set
    var surfacePrimaryBorder by mutableStateOf(surfacePrimaryBorder, structuralEqualityPolicy())
        internal set

    var textStrong by mutableStateOf(textStrong, structuralEqualityPolicy())
        internal set
    var textNeutral by mutableStateOf(textNeutral, structuralEqualityPolicy())
        internal set
    var textWeak by mutableStateOf(textWeak, structuralEqualityPolicy())
        internal set

    var error by mutableStateOf(error, structuralEqualityPolicy())
        internal set
    var onError by mutableStateOf(onError, structuralEqualityPolicy())
        internal set
    var darkErrorText by mutableStateOf(darkErrorText, structuralEqualityPolicy())
        internal set
    var surfaceBorderError by mutableStateOf(surfaceBorderError, structuralEqualityPolicy())
        internal set

    var success by mutableStateOf(success, structuralEqualityPolicy())
        internal set
    var onSuccess by mutableStateOf(onSuccess, structuralEqualityPolicy())
        internal set
    var darkSuccessText by mutableStateOf(darkSuccessText, structuralEqualityPolicy())
        internal set
    var surfaceBorderSuccess by mutableStateOf(surfaceBorderSuccess, structuralEqualityPolicy())
        internal set

    var info by mutableStateOf(info, structuralEqualityPolicy())
        internal set
    var onInfo by mutableStateOf(onInfo, structuralEqualityPolicy())
        internal set
    var darkInfoText by mutableStateOf(darkInfoText, structuralEqualityPolicy())
        internal set
    var surfaceBorderInfo by mutableStateOf(surfaceBorderInfo, structuralEqualityPolicy())
        internal set
}