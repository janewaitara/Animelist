package com.mumbicodes.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mumbicodes.designsystem.atoms.Image
import com.mumbicodes.designsystem.atoms.Text
import com.mumbicodes.designsystem.theme.AnimeListTheme
import com.mumbicodes.designsystem.theme.AnimeTheme
import com.mumbicodes.designsystem.theme.Background_dark
import com.mumbicodes.designsystem.theme.Grey_100

@Composable
fun VerticalCharacterComponent(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    coverImageUrl: String?,
    characterName: String
) {
    Box(
        modifier = modifier
            .width(120.dp)
            .clip(shape = AnimeTheme.shapes.mediumShape)
            .background(color = AnimeTheme.colors.background)
            .clickable {
                onClick()
            }
    ) {
        Image(
            modifier = Modifier
                .clip(shape = AnimeTheme.shapes.mediumShape)
                .size(width = 120.dp, height = 144.dp),
            contentDescription = "$characterName cover image",
            coverImageUrl = coverImageUrl,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier.fillMaxWidth()
                .background(color = Background_dark.copy(alpha = 0.75f))
                .padding(vertical = AnimeTheme.space.space8dp, horizontal = AnimeTheme.space.space8dp)
                .align(Alignment.BottomStart),
            text = characterName,
            color = Grey_100,
            overflow = TextOverflow.Ellipsis,
            style = AnimeTheme.typography.bodyExtraSmall,
            textAlign = TextAlign.Start,
            maxLines = 2
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, device = Devices.PIXEL, showBackground = true)
@Composable
fun VerticalAnimeComponentPreview() {
    AnimeListTheme {
        Row {
            VerticalCharacterComponent(
                characterName = "AnimeTitle dweewrwqqsqwdwdwdqwsqwsdwdewdewfref",
                coverImageUrl = null,
                onClick = {}
            )
            Spacer(modifier = Modifier.width(AnimeTheme.space.space16dp))

            VerticalCharacterComponent(
                characterName = "AnimeTitle",
                coverImageUrl = null,
                onClick = {}
            )
        }
    }
}