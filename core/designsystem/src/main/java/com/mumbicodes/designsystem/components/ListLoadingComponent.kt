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
fun ListLoadingComponent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AnimeTheme.colors.background)
    ) {
        // The mask with the shimmer
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(AnimeTheme.space.space24dp)
                .shimmerEffect()
        ) {}

        // The white items
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            repeat(6) {
                Row(
                    modifier = Modifier.height(120.dp)
                ) {
                    Spacer(
                        Modifier
                            .padding(start = 120.dp)
                            .width(AnimeTheme.space.space16dp)
                            .fillMaxHeight()
                            .background(
                                AnimeTheme.colors.background
                            )
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {
                        Spacer(
                            Modifier
                                .padding(bottom = 16.dp)
                                .height(AnimeTheme.space.space16dp)
                                .fillMaxWidth()
                                .background(
                                    AnimeTheme.colors.background
                                )
                        )
                        Spacer(
                            Modifier
                                .padding(bottom = 24.dp)
                                .height(AnimeTheme.space.space12dp)
                                .fillMaxWidth()
                                .background(
                                    AnimeTheme.colors.background
                                )
                        )
                        Spacer(
                            Modifier
                                .padding(bottom = 12.dp)
                                .height(AnimeTheme.space.space24dp)
                                .fillMaxWidth()
                                .background(
                                    AnimeTheme.colors.background
                                )
                        )
                        Spacer(
                            Modifier
                                .height(AnimeTheme.space.space24dp)
                                .fillMaxWidth()
                                .background(
                                    AnimeTheme.colors.background
                                )
                        )
                    }
                }
                Spacer(
                    Modifier
                        .height(AnimeTheme.space.space16dp)
                        .fillMaxWidth()
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
fun ListLoadingComponentPreview() {
    AnimeListTheme {
        ListLoadingComponent()
    }
}