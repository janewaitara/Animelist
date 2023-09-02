package com.mumbicodes.anime

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mumbicodes.designsystem.R
import com.mumbicodes.designsystem.atoms.CircleDecoration
import com.mumbicodes.designsystem.atoms.Image
import com.mumbicodes.designsystem.atoms.Text
import com.mumbicodes.designsystem.theme.AnimeListTheme
import com.mumbicodes.designsystem.theme.AnimeTheme
import com.mumbicodes.model.data.Anime
import com.mumbicodes.model.data.AnimeTitle

@Composable
fun AnimeDetailsRoute(
    modifier: Modifier = Modifier,
    animeViewModel: AnimeViewModel = hiltViewModel(),
    onCharacterClicked: () -> Unit
) {
    val animeDetailsState: AnimeDetailsScreenUiState
        by animeViewModel.animeDetails.collectAsStateWithLifecycle()

    AnimeDetailsScreen(
        modifier = modifier,
        animeDetailsState = animeDetailsState,
        onCharacterClicked = onCharacterClicked
    )
}

@Composable
fun AnimeDetailsScreen(
    modifier: Modifier = Modifier,
    animeDetailsState: AnimeDetailsScreenUiState,
    onCharacterClicked: () -> Unit
) {
    when (animeDetailsState) {
        is AnimeDetailsScreenUiState.AnimeDetails -> {
            AnimeDetailsContent(
                modifier = modifier,
                anime = animeDetailsState.animeDetails,
                onCharacterClicked = onCharacterClicked
            )
        }

        is AnimeDetailsScreenUiState.Error -> {
            TODO()
        }

        is AnimeDetailsScreenUiState.Loading -> {
            Text(
                text = "Loading",
                modifier = modifier.clickable {
                    onCharacterClicked()
                }
            )
        }
    }
}

@Composable
fun AnimeDetailsContent(
    modifier: Modifier,
    anime: Anime,
    onCharacterClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AnimeDetailsHeader(
            modifier = Modifier,
            anime = anime
        )
    }
}

@Composable
fun AnimeDetailsHeader(
    modifier: Modifier = Modifier,
    anime: Anime
) {
    ConstraintLayout(
        modifier = modifier.fillMaxWidth()
    ) {
        val (animeCoverImage, animeBanner, animeTextDetails) = createRefs()
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(214.dp)
                .constrainAs(animeCoverImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            contentDescription = "${anime.title} cover image",
            coverImageUrl = anime.bannerImage,
            contentScale = ContentScale.Crop
        )

        Image(
            modifier = Modifier
                .padding(start = AnimeTheme.space.space20dp)
                .offset(y = (-36).dp)
                .clip(shape = AnimeTheme.shapes.mediumShape)
                .width(120.dp)
                .height(168.dp)
                .constrainAs(animeBanner) {
                    top.linkTo(animeCoverImage.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(animeTextDetails.start)
                },
            contentDescription = "${anime.title} cover image",
            coverImageUrl = anime.coverImage,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .constrainAs(animeTextDetails) {
                    top.linkTo(animeCoverImage.bottom)
                    start.linkTo(animeBanner.end)
                    end.linkTo(parent.end)

                    width = Dimension.fillToConstraints
                }
                .padding(
                    start = AnimeTheme.space.space12dp,
                    top = AnimeTheme.space.space20dp,
                    end = AnimeTheme.space.space20dp
                ),
            verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space8dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = anime.title?.english ?: anime.title?.romaji
                    ?: anime.title?.native ?: "",
                color = AnimeTheme.colors.textStrong,
                style = AnimeTheme.typography.titleSmall,
                textAlign = TextAlign.Start
            )
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AnimeTheme.space.space8dp)
            ) {
                anime.format?.name?.let {
                    Text(
                        modifier = Modifier,
                        text = it,
                        color = AnimeTheme.colors.textStrong,
                        style = AnimeTheme.typography.bodySmall,
                        textAlign = TextAlign.Start
                    )
                }

                CircleDecoration()

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = AnimeTheme.typography.bodySmall.toSpanStyle()
                                .copy(
                                    color = AnimeTheme.colors.textStrong
                                )
                        ) {
                            append("${anime.episodes} ")
                        }
                        withStyle(
                            style = AnimeTheme.typography.bodySmall.toSpanStyle()
                                .copy(
                                    color = AnimeTheme.colors.textWeak
                                )
                        ) {
                            append(stringResource(id = R.string.numberOfEpisodes))
                        }
                    },
                    color = AnimeTheme.colors.textWeak,
                    style = AnimeTheme.typography.bodySmall,
                    textAlign = TextAlign.Start,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                CircleDecoration()
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = AnimeTheme.typography.bodySmall.toSpanStyle()
                                .copy(
                                    color = AnimeTheme.colors.textStrong
                                )
                        ) {
                            append("${anime.duration} ")
                        }
                        withStyle(
                            style = AnimeTheme.typography.bodySmall.toSpanStyle()
                                .copy(
                                    color = AnimeTheme.colors.textWeak
                                )
                        ) {
                            append(stringResource(id = R.string.episodesDuration))
                        }
                    },
                    style = AnimeTheme.typography.bodySmall,
                    textAlign = TextAlign.Start,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview
@Composable
fun AnimeDetailsHeaderPreview() {
    AnimeListTheme {
        AnimeDetailsHeader(
            anime = Anime(
                id = 1,
                title = AnimeTitle(english = "Anime Title", native = null, romaji = null)
            )
        )
    }
}