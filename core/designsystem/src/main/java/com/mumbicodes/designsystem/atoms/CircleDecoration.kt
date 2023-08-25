package com.mumbicodes.designsystem.atoms

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.dp
import com.mumbicodes.designsystem.theme.AnimeTheme

@Composable
fun CircleDecoration(
    dotIndicatorColor: Color = AnimeTheme.colors.primary
) {
    Canvas(
        modifier = Modifier.wrapContentSize()
    ) {
        drawCircle(
            color = dotIndicatorColor,
            radius = 2.dp.toPx(),
            alpha = 1f,
            style = Fill
        )
    }
}