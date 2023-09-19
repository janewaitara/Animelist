package com.mumbicodes.designsystem.atoms

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mumbicodes.designsystem.theme.AnimeListTheme
import com.mumbicodes.designsystem.theme.AnimeTheme
import com.mumbicodes.designsystem.theme.LocalContentColor

@Composable
fun Badge(
    modifier: Modifier = Modifier,
    text: String,
    badgeColor: BadgeColor = BadgeColor.Primary,
    shape: Shape = AnimeTheme.shapes.mediumShape
) {
    val backgroundColor: Color = when (badgeColor) {
        BadgeColor.Primary -> AnimeTheme.colors.onPrimary
        BadgeColor.Info -> AnimeTheme.colors.onInfo
    }
    val contentColor: Color = when (badgeColor) {
        BadgeColor.Primary -> AnimeTheme.colors.surfacePrimaryHover
        BadgeColor.Info -> AnimeTheme.colors.darkInfoText
    }
    val borderColor: Color = when (badgeColor) {
        BadgeColor.Primary -> AnimeTheme.colors.surfacePrimaryBorder
        BadgeColor.Info -> AnimeTheme.colors.surfaceBorderInfo
    }

    Surface(
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        border = BorderStroke(width = 1.dp, color = borderColor)
    ) {
        CompositionLocalProvider(
            LocalContentColor provides contentColor
        ) {
            Box(
                modifier = modifier.padding(
                    horizontal = AnimeTheme.space.space8dp,
                    vertical = 2.dp
                )
            ) {
                Text(
                    text = text,
                    style = AnimeTheme.typography.bodySmall,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

enum class BadgeColor {
    Primary, Info
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BadgePreview() {
    AnimeListTheme {
        Column(
            modifier = Modifier
                .background(color = AnimeTheme.colors.background)
                .padding(48.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Badge(text = "Badge")
            Badge(
                text = "Badge",
                badgeColor = BadgeColor.Info
            )
        }
    }
}