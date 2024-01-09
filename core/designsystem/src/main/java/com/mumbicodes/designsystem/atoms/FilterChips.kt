package com.mumbicodes.designsystem.atoms

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mumbicodes.designsystem.theme.AnimeListTheme
import com.mumbicodes.designsystem.theme.AnimeTheme
import com.mumbicodes.designsystem.theme.LocalContentColor

@Composable
fun FilterChips(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit = {}
) {
    val backgroundColor: Color = when (selected) {
        true -> AnimeTheme.colors.onPrimary
        false -> AnimeTheme.colors.background
    }
    val contentColor: Color = when (selected) {
        true -> AnimeTheme.colors.surfacePrimaryHover
        false -> AnimeTheme.colors.notSelectedFilterChipContentColor
    }
    val borderColor: Color = when (selected) {
        true -> AnimeTheme.colors.surfacePrimaryBorder
        false -> AnimeTheme.colors.notSelectedFilterChipBorderColor
    }

    Surface(
        modifier = Modifier.clickable {
            onClick()
        },
        shape = AnimeTheme.shapes.mediumShape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        border = BorderStroke(width = 1.dp, color = borderColor)
    ) {
        CompositionLocalProvider(
            LocalContentColor provides contentColor
        ) {
            Box(
                modifier = modifier.padding(
                    horizontal = AnimeTheme.space.space16dp,
                    vertical = AnimeTheme.space.space4dp
                )
            ) {
                Text(
                    text = text,
                    style = AnimeTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FilterChipsPreview() {
    AnimeListTheme {
        Column(
            modifier = Modifier
                .background(color = AnimeTheme.colors.background)
                .padding(48.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FilterChips(text = "Badge", selected = true)
            FilterChips(
                text = "Badge",
                selected = false
            )
        }
    }
}