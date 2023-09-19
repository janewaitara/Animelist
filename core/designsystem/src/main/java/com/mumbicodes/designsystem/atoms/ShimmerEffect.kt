package com.mumbicodes.designsystem.atoms

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.mumbicodes.designsystem.theme.AnimeListTheme
import com.mumbicodes.designsystem.theme.AnimeTheme

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }

    val shimmerColors = listOf(
        AnimeTheme.colors.shimmerEffectColor.copy(alpha = 0.5f),
        AnimeTheme.colors.shimmerEffectColor,
        AnimeTheme.colors.shimmerEffectColor.copy(alpha = 0.5f)
    )
    val transition = rememberInfiniteTransition()

    val translateAnimation by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            )
        )
    )

    background(
        brush = Brush.horizontalGradient(
            colors = shimmerColors,
            startX = translateAnimation,
            endX = translateAnimation + size.width.toFloat()
        )
    ).onGloballyPositioned {
        size = it.size
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ShimmerEffectPreview() {
    AnimeListTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AnimeTheme.colors.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .shimmerEffect()
            ) {}
        }
    }
}