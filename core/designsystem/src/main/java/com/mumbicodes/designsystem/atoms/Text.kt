package com.mumbicodes.designsystem.atoms

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import com.mumbicodes.designsystem.theme.AnimeTheme
import com.mumbicodes.designsystem.theme.LocalContentColor

/**
 * @param text - the text to be displayed
 * @param modifier - the Modifier to be applied to this layout node
 * @param color - Color to apply to the text. If Color.Unspecified, and style has no color set, this will be LocalContentColor.
 * @param textDecoration - the decorations to paint on the text (e.g., an underline). See TextStyle.textDecoration.
 * @param textAlign - the alignment of the text within the lines of the paragraph. See TextStyle.textAlign.
 * @param overflow - how visual overflow should be handled.
 * @param softWrap - whether the text should break at soft line breaks. If false, the glyphs in the text will be positioned as if there was unlimited horizontal space. If softWrap is false, overflow and TextAlign may have unexpected effects.
 * @param maxLines - An optional maximum number of lines for the text to span, wrapping if necessary. If the text exceeds the given number of lines, it will be truncated according to overflow and softWrap. It is required that 1 <= minLines <= maxLines.
 * @param minLines - The minimum height in terms of minimum number of visible lines. It is required that 1 <= minLines <= maxLines.
 * @param onTextLayout - callback that is executed when a new text layout is calculated. A TextLayoutResult object that callback provides contains paragraph information, size of the text, baselines and other details. The callback can be used to add additional decoration or functionality to the text. For example, to draw selection around the text.
 * @param style - style configuration for the text such as color, font, line height etc.*/
@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = AnimeTheme.typography.bodyMedium
) {
    val textColor = color.takeOrElse {
        style.color.takeOrElse {
            LocalContentColor.current
        }
    }

    val mergedStyle = style.merge(
        TextStyle(
            color = textColor,
            textAlign = textAlign,
            textDecoration = textDecoration
        )
    )
    BasicText(
        text,
        modifier,
        mergedStyle,
        onTextLayout,
        overflow,
        softWrap,
        maxLines,
        minLines
    )
}

/**
 * @param text - the text to be displayed
 * @param modifier - the Modifier to be applied to this layout node
 * @param color - Color to apply to the text. If Color.Unspecified, and style has no color set, this will be LocalContentColor.
 * @param textDecoration - the decorations to paint on the text (e.g., an underline). See TextStyle.textDecoration.
 * @param textAlign - the alignment of the text within the lines of the paragraph. See TextStyle.textAlign.
 * @param overflow - how visual overflow should be handled.
 * @param softWrap - whether the text should break at soft line breaks. If false, the glyphs in the text will be positioned as if there was unlimited horizontal space. If softWrap is false, overflow and TextAlign may have unexpected effects.
 * @param maxLines - An optional maximum number of lines for the text to span, wrapping if necessary. If the text exceeds the given number of lines, it will be truncated according to overflow and softWrap. It is required that 1 <= minLines <= maxLines.
 * @param minLines - The minimum height in terms of minimum number of visible lines. It is required that 1 <= minLines <= maxLines.
 * @param onTextLayout - callback that is executed when a new text layout is calculated. A TextLayoutResult object that callback provides contains paragraph information, size of the text, baselines and other details. The callback can be used to add additional decoration or functionality to the text. For example, to draw selection around the text.
 * @param style - style configuration for the text such as color, font, line height etc.*/
@Composable
fun Text(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = AnimeTheme.typography.bodyMedium
) {
    val textColor = color.takeOrElse {
        style.color.takeOrElse {
            LocalContentColor.current
        }
    }

    val mergedStyle = style.merge(
        TextStyle(
            color = textColor,
            textAlign = textAlign,
            textDecoration = textDecoration
        )
    )
    BasicText(
        text,
        modifier,
        mergedStyle,
        onTextLayout,
        overflow,
        softWrap,
        maxLines,
        minLines
    )
}