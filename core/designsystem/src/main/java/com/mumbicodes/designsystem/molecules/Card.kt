package com.mumbicodes.designsystem.molecules

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.mumbicodes.designsystem.atoms.Surface
import com.mumbicodes.designsystem.theme.AnimeTheme
import com.mumbicodes.designsystem.theme.contentColorFor
import com.mumbicodes.designsystem.theme.extraSmallShadow

/**
 * Cards contain contain content and actions that relate information about a subject.
 * All the cards have a [extraSmallShadow]
 *
 * @param onClick - called when this card is clicked
 * @param modifier the [Modifier] to be applied to this card
 * @param shape defines the shape of this card's container
 * @param backgroundColor this is the card background.
 * @param contentColor this is the color of the card content
 */
@Composable
fun Card(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = AnimeTheme.shapes.smallShape,
    backgroundColor: Color = AnimeTheme.colors.cardColors,
    contentColor: Color = contentColorFor(backgroundColor),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier.extraSmallShadow(),
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        shape = shape,
        interactionSource = interactionSource
    ) {
        content()
    }
}