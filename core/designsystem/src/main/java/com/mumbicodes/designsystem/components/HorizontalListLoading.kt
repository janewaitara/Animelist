package com.mumbicodes.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mumbicodes.designsystem.atoms.shimmerEffect
import com.mumbicodes.designsystem.theme.AnimeListTheme
import com.mumbicodes.designsystem.theme.AnimeTheme

@Composable
fun HorizontalListLoading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(color = AnimeTheme.colors.background)
    ) {
        // The mask with the shimmer
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = AnimeTheme.space.space24dp,
                    top = AnimeTheme.space.space24dp,
                    bottom = AnimeTheme.space.space24dp
                )
                .shimmerEffect()
        ) {}

        // The white items
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = AnimeTheme.space.space24dp,
                    top = AnimeTheme.space.space24dp,
                    bottom = AnimeTheme.space.space24dp
                )
        ) {
            repeat(6) {
                Column(
                    modifier = Modifier.width(120.dp)
                ) {
                    Spacer(
                        Modifier
                            .padding(top = 120.dp)
                            .height(AnimeTheme.space.space16dp)
                            .fillMaxWidth()
                            .background(
                                AnimeTheme.colors.background
                            )
                    )
                }
                Spacer(
                    Modifier
                        .width(AnimeTheme.space.space16dp)
                        .fillMaxHeight()
                        .background(
                            AnimeTheme.colors.background
                        )
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HorizontalListLoadingPreview() {
    AnimeListTheme {
        HorizontalListLoading()
    }
}