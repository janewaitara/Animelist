package com.mumbicodes.designsystem.atoms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toolingGraphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.mumbicodes.designsystem.theme.LocalContentColor

/**
 * An icon component that draws painter using tint, with a default value of LocalContentColor. If painter has no intrinsic size, this component will use the recommended default size. Icon is an opinionated component designed to be used with single-color icons so that they can be tinted correctly for the component they are placed in. For multicolored icons and icons that should not be tinted, use Color.Unspecified for tint. For generic images that should not be tinted, and do not follow the recommended icon size, use the generic androidx.compose.foundation.Image instead. For a clickable icon, see IconButton.
 *
 * @param painter - Painter to draw inside this icon
 * @param contentDescription - text used by accessibility services to describe what this icon represents. This should always be provided unless this icon is used for decorative purposes, and does not represent a meaningful action that a user can take. This text should be localized, such as by using androidx.compose.ui.res.stringResource or similar
 * @param modifier - the Modifier to be applied to this icon
 * @param tint - tint to be applied to painter. If Color.Unspecified is provided, then no tint is applied.*/
@Composable
fun Icon(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current
) {
    val colorFilter = if (tint == Color.Unspecified) null else ColorFilter.tint(tint)
    val semantics =
        if (contentDescription != null) {
            Modifier.semantics {
                this.contentDescription = contentDescription
                this.role = Role.Image
            }
        } else {
            Modifier
        }
    Box(
        modifier
            .toolingGraphicsLayer()
            .defaultSizeFor(painter)
            .paint(painter, colorFilter = colorFilter, contentScale = ContentScale.Fit)
            .then(semantics)
    )
}

private fun Modifier.defaultSizeFor(painter: Painter) =
    this.then(
        if (painter.intrinsicSize == Size.Unspecified || painter.intrinsicSize.isInfinite()) {
            Modifier.size(24.dp)
        } else {
            Modifier
        }
    )

private fun Size.isInfinite() = width.isInfinite() && height.isInfinite()