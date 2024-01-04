package com.mumbicodes.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mumbicodes.designsystem.R
import com.mumbicodes.designsystem.atoms.Image
import com.mumbicodes.designsystem.atoms.Text
import com.mumbicodes.designsystem.theme.AnimeListTheme
import com.mumbicodes.designsystem.theme.AnimeTheme
import com.mumbicodes.designsystem.theme.extraSmallShadow

@Composable
fun PagedAnimeComponent(
    modifier: Modifier = Modifier,
    onAnimeClicked: () -> Unit,
    coverImageUrl: String?,
    animeTitle: String,
    numberOfEpisodes: Int,
    episodeDuration: Int
) {
    Column(
        modifier = modifier
            .padding(
                top = AnimeTheme.space.space48dp,
                bottom = AnimeTheme.space.space48dp
            )
            .clickable {
                onAnimeClicked()
            },
        verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space24dp)
    ) {
        Image(
            modifier = Modifier
                .clip(shape = AnimeTheme.shapes.mediumShape)
                .fillMaxWidth()
                .weight(1f),
            coverImageUrl = coverImageUrl,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .extraSmallShadow()
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = AnimeTheme.colors.cardColors,
                    shape = AnimeTheme.shapes.smallShape
                )
                .padding(AnimeTheme.space.space16dp),
            verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space16dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = animeTitle,
                color = AnimeTheme.colors.textNeutral,
                style = AnimeTheme.typography.subTitle,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier.height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(AnimeTheme.space.space8dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$numberOfEpisodes",
                        color = AnimeTheme.colors.primary,
                        style = AnimeTheme.typography.bodyLargeBold,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = stringResource(id = R.string.numberOfEpisodes),
                        color = AnimeTheme.colors.textWeak,
                        style = AnimeTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(2.dp)
                        .background(color = AnimeTheme.colors.surfacePrimaryDisabled)
                )
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$episodeDuration",
                        color = AnimeTheme.colors.primary,
                        style = AnimeTheme.typography.bodyLargeBold,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = stringResource(id = R.string.episodesDuration),
                        color = AnimeTheme.colors.textWeak,
                        style = AnimeTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, device = Devices.PIXEL)
@Composable
fun PagedAnimeComponentPreview() {
    AnimeListTheme {
        Column {
            PagedAnimeComponent(
                animeTitle = "AnimeTitle",
                numberOfEpisodes = 12,
                episodeDuration = 24,
                coverImageUrl = null,
                onAnimeClicked = {}
            )
        }
    }
}