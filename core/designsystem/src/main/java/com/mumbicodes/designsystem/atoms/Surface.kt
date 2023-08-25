package com.mumbicodes.designsystem.atoms

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import com.mumbicodes.designsystem.theme.AnimeListColors
import com.mumbicodes.designsystem.theme.AnimeTheme
import com.mumbicodes.designsystem.theme.LocalContentColor
import com.mumbicodes.designsystem.theme.contentColorFor

/**
 *
 * This version of Surface is responsible for a click handling as well as everything else that a
 * regular Surface does:
 *
 * This clickable Surface is responsible for:
 *
 * 1) Clipping: Surface clips its children to the shape specified by [shape]
 *
 * 2) Background: Surface fills the shape specified by [shape] with the [backgroundColor].
 *
 * 3) Content color: Surface uses [contentColor] to specify a preferred color for the content of
 * this surface - this is used by the [Text] and [Icon] components as a default color. If no
 * [contentColor] is set, this surface will try and match its background color to a color defined in
 * the theme [AnimeListColors], and return the corresponding content color. If [backgroundColor] is not part
 * of the theme palette, [contentColor] will keep the same value set above this Surface.
 *
 * 4) Click handling. This version of surface will react to the clicks, calling [onClick] lambda,
 * updating the [interactionSource] when [PressInteraction] occurs, and showing ripple indication in
 * response to press events. If you don't need click handling, consider using the Surface function
 * that doesn't require [onClick] param. If you need to set a custom label for the [onClick], apply
 * a `Modifier.semantics { onClick(label = "YOUR_LABEL", action = null) }` to the Surface.
 *
 * 5) Semantics for clicks. Just like with [Modifier.clickable], clickable version of Surface will
 * produce semantics to indicate that it is clicked. Button semantic role is set by default, you
 * may specify one by passing a desired [Role] with a [Modifier.semantics].
 *
 * To manually retrieve the content color inside a surface, use [LocalContentColor].
 *
 * @param onClick callback to be called when the surface is clicked
 * @param modifier Modifier to be applied to the layout corresponding to the surface
 * @param enabled Controls the enabled state of the surface. When `false`, this surface will not be clickable
 * @param shape Defines the surface's shape as well its shadow.
 * @param backgroundColor The background color. Use [Color.Transparent] to have no color.
 * @param contentColor The preferred content color provided by this Surface to its children.
 * Defaults to either the matching content color for [backgroundColor], or if [backgroundColor] is not a color from the
 * theme, this will keep the same value set above this Surface.
 * @param border - Optional border to draw on top of the surface
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this Surface. You can create and pass in your own remembered [MutableInteractionSource] if
 * you want to observe [Interaction]s and customize the appearance / behavior of this Surface in
 * different [Interaction]s.
 */
@Composable
fun Surface(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RectangleShape,
    backgroundColor: Color = AnimeTheme.colors.cardColors,
    contentColor: Color = contentColorFor(backgroundColor),
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalContentColor provides contentColor
    ) {
        Box(
            modifier = modifier
                .surface(
                    shape = shape,
                    backgroundColor = backgroundColor,
                    border = border
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    role = Role.Button,
                    enabled = enabled,
                    onClick = onClick
                ),
            propagateMinConstraints = true
        ) {
            content()
        }
    }
}

/**
 * See the other overload for a clickable surface.
 *
 * The Surface is responsible for:
 *
 * 1) Clipping: Surface clips its children to the shape specified by [shape]
 *
 * 2) Borders: If [shape] has a border, then it will also be drawn.
 *
 * 3) Background: Surface fills the shape specified by [shape] with the [backgroundColor].
 *
 * 4) Content color: Surface uses [contentColor] to specify a preferred color for the content of
 * this surface - this is used by the [Text] and [Icon] components as a default color.
 * If no [contentColor] is set, this surface will try and match its background color to a color
 * defined in the theme [AnimeListColors], and return the corresponding content color. For example, if
 * the [backgroundColor] of this surface is [AnimeListColors.primary], [contentColor] will be set to
 * [AnimeListColors.onPrimary]. If [backgroundColor] is not part of the theme palette, [contentColor] will keep
 * the same value set above this Surface.
 *
 * To manually retrieve the content color inside a surface, use [LocalContentColor].
 *
 * 5) Blocking touch propagation behind the surface.
 *
 * @param modifier Modifier to be applied to the layout corresponding to the surface
 * @param shape Defines the surface's shape as well its shadow.
 * @param backgroundColor The background color. Use [Color.Transparent] to have no color.
 * @param contentColor The preferred content color provided by this Surface to its children.
 * Defaults to either the matching content color for [backgroundColor], or if [backgroundColor] is not a color from the
 * theme, this will keep the same value set above this Surface.
 * @param border Optional border to draw on top of the surface
 */
@Composable
fun Surface(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    backgroundColor: Color = AnimeTheme.colors.cardColors,
    contentColor: Color = contentColorFor(backgroundColor),
    border: BorderStroke? = null,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalContentColor provides contentColor
    ) {
        Box(
            modifier = modifier
                .surface(
                    shape = shape,
                    backgroundColor = backgroundColor,
                    border = border
                )
                .semantics(mergeDescendants = false) {
                    isContainer = true
                }
                .pointerInput(Unit) {},
            propagateMinConstraints = true
        ) {
            content()
        }
    }
}

private fun Modifier.surface(
    shape: Shape,
    backgroundColor: Color,
    border: BorderStroke?
) = this
    .then(if (border != null) Modifier.border(border, shape) else Modifier)
    .background(color = backgroundColor, shape = shape)
    .clip(shape)