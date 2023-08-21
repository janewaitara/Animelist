package com.mumbicodes.designsystem.atoms

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mumbicodes.designsystem.theme.AnimeListTheme
import com.mumbicodes.designsystem.theme.AnimeListTypography
import com.mumbicodes.designsystem.theme.AnimeTheme
import com.mumbicodes.designsystem.theme.LocalContentColor
import com.mumbicodes.designsystem.theme.ProvideTextStyle
import com.mumbicodes.designsystem.theme.contentColorFor

/**
 * Primary buttons are high-emphasis buttons.
 *
 * Choose the best button for an action based on the amount of emphasis it needs. The more important
 * an action is, the higher emphasis its button should be.
 *
 * The default text style for internal [Text] components will be set to [AnimeListTypography.bodyMediumBold].
 *
 * @param onClick called when this button is clicked
 * @param modifier the [Modifier] to be applied to this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param shape defines the shape of this button's container
 * @param color the background color for the button
 * @param border the border to draw around the container of this button
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 */

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: Color = AnimeTheme.colors.primary,
    shape: Shape = AnimeTheme.shapes.smallShape,
    contentColor: Color = contentColorFor(color),
    iconLeft: @Composable (() -> Unit)? = null,
    iconRight: @Composable (() -> Unit)? = null,
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    val backgroundColor = when (enabled) {
        true -> color
        false -> AnimeTheme.colors.surfacePrimaryDisabled
    }
    Surface(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        border = border,
        interactionSource = interactionSource
    ) {
        CompositionLocalProvider(
            LocalContentColor provides contentColor
        ) {
            ProvideTextStyle(value = AnimeTheme.typography.bodyMediumBold) {
                Row(
                    Modifier
                        .defaultMinSize(
                            minWidth = 64.dp,
                            minHeight = 40.dp
                        )
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    iconLeft?.let {
                        iconLeft()
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    content()
                    iconRight?.let {
                        Spacer(modifier = Modifier.width(8.dp))
                        iconRight()
                    }
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ButtonsPreview() {
    AnimeListTheme {
        Column {
            PrimaryButton(onClick = {}) {
                Text(text = "Button")
            }
        }
    }
}