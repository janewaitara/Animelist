package com.mumbicodes.home.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.mumbicodes.designsystem.atoms.Image
import com.mumbicodes.designsystem.atoms.Text
import com.mumbicodes.designsystem.theme.AnimeListTheme
import com.mumbicodes.designsystem.theme.AnimeTheme

@Composable
fun VerticalAnimeComponent(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    coverImageUrl: String?,
    animeTitle: String
) {
    ConstraintLayout(
        modifier = modifier
            .wrapContentSize()
            .background(color = AnimeTheme.colors.background)
            .clickable {
                onClick()
            }
    ) {
        val (image, title) = createRefs()
        Image(
            modifier = Modifier
                .clip(shape = AnimeTheme.shapes.mediumShape)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            contentDescription = "$animeTitle cover image",
            coverImageUrl = coverImageUrl,
            contentScale = ContentScale.FillHeight
        )
        Text(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(image.bottom, margin = 4.dp)
                    start.linkTo(image.start)
                    end.linkTo(image.end)

                    width = Dimension.fillToConstraints
                },
            text = animeTitle,
            color = AnimeTheme.colors.textWeak,
            overflow = TextOverflow.Ellipsis,
            style = AnimeTheme.typography.bodyMedium,
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
            VerticalAnimeComponent(
                animeTitle = "AnimeTitle dweewrwqqsqwdwdwdqwsqwsdwdewdewfref",
                coverImageUrl = null,
                onClick = {}
            )
            Spacer(modifier = Modifier.width(AnimeTheme.space.space16dp))

            VerticalAnimeComponent(
                animeTitle = "AnimeTitle",
                coverImageUrl = null,
                onClick = {}
            )
        }
    }
}