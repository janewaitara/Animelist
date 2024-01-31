package com.mumbicodes.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mumbicodes.designsystem.R
import com.mumbicodes.designsystem.atoms.CircleDecoration
import com.mumbicodes.designsystem.atoms.Image
import com.mumbicodes.designsystem.atoms.Text
import com.mumbicodes.designsystem.molecules.Card
import com.mumbicodes.designsystem.theme.AnimeListTheme
import com.mumbicodes.designsystem.theme.AnimeTheme

@Composable
fun SearchAnimeComponent(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    coverImageUrl: String?,
    animeEnglishTitle: String,
    animeNativeTitle: String,
    numberOfEpisodes: Int,
    episodeDuration: Int
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .clip(shape = AnimeTheme.shapes.mediumShape)
            .background(color = AnimeTheme.colors.cardColors)
            .height(96.dp),
        shape = AnimeTheme.shapes.mediumShape
    ) {
        Row(
            modifier = Modifier.padding(
                end = AnimeTheme.space.space16dp
            )
        ) {
            Image(
                modifier = Modifier
                    .clip(shape = AnimeTheme.shapes.mediumShape)
                    .size(width = 114.dp, height = 96.dp),
                coverImageUrl = coverImageUrl,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(AnimeTheme.space.space16dp))

            Column(
                modifier = Modifier
                    .padding(top = AnimeTheme.space.space8dp, bottom = AnimeTheme.space.space16dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space4dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = animeEnglishTitle,
                    color = AnimeTheme.colors.textNeutral,
                    style = AnimeTheme.typography.bodyMediumBold,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = animeNativeTitle,
                    color = AnimeTheme.colors.primary,
                    style = AnimeTheme.typography.bodyExtraSmall,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.weight(1f))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(AnimeTheme.space.space8dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = AnimeTheme.typography.bodyExtraSmall.toSpanStyle()
                                    .copy(
                                        color = AnimeTheme.colors.textStrong
                                    )
                            ) {
                                append("$numberOfEpisodes ")
                            }
                            withStyle(
                                style = AnimeTheme.typography.bodyExtraSmall.toSpanStyle()
                                    .copy(
                                        color = AnimeTheme.colors.textWeak
                                    )
                            ) {
                                append(stringResource(id = R.string.numberOfEpisodes))
                            }
                        },
                        color = AnimeTheme.colors.textWeak,
                        style = AnimeTheme.typography.bodyExtraSmall,
                        textAlign = TextAlign.Start,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    CircleDecoration()
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = AnimeTheme.typography.bodyExtraSmall.toSpanStyle()
                                    .copy(
                                        color = AnimeTheme.colors.textStrong
                                    )
                            ) {
                                append("$episodeDuration ")
                            }
                            withStyle(
                                style = AnimeTheme.typography.bodyExtraSmall.toSpanStyle()
                                    .copy(
                                        color = AnimeTheme.colors.textWeak
                                    )
                            ) {
                                append(stringResource(id = R.string.episodesDuration))
                            }
                        },
                        style = AnimeTheme.typography.bodyExtraSmall,
                        textAlign = TextAlign.Start,
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
fun SearchAnimeComponentPreview() {
    AnimeListTheme {
        Column {
            SearchAnimeComponent(
                animeEnglishTitle = "AnimeTitle",
                animeNativeTitle = "#$$$¢¢¢",
                numberOfEpisodes = 12,
                episodeDuration = 24,
                coverImageUrl = null,
                onClick = {}
            )

            Spacer(modifier = Modifier.height(AnimeTheme.space.space16dp))

            SearchAnimeComponent(
                animeEnglishTitle = "AnimeTitle",
                animeNativeTitle = "#$$$¢¢¢",
                numberOfEpisodes = 12,
                episodeDuration = 24,
                coverImageUrl = null,
                onClick = {}
            )
        }
    }
}