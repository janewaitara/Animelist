package com.mumbicodes.anime

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.mumbicodes.designsystem.atoms.Badge
import com.mumbicodes.designsystem.atoms.CircleDecoration
import com.mumbicodes.designsystem.atoms.Image
import com.mumbicodes.designsystem.atoms.PrimaryButton
import com.mumbicodes.designsystem.atoms.Surface
import com.mumbicodes.designsystem.atoms.Text
import com.mumbicodes.designsystem.components.HorizontalAnimeComponent
import com.mumbicodes.designsystem.components.VerticalCharacterComponent
import com.mumbicodes.designsystem.molecules.AnimeSection
import com.mumbicodes.designsystem.theme.AnimeListTheme
import com.mumbicodes.designsystem.theme.AnimeTheme
import com.mumbicodes.model.data.Anime
import com.mumbicodes.model.data.AnimeTitle
import com.mumbicodes.model.data.Character

@Composable
fun AnimeDetailsRoute(
    modifier: Modifier = Modifier,
    animeViewModel: AnimeViewModel = hiltViewModel(),
    onCharacterClicked: (Int) -> Unit,
    onAnimeClicked: (Int) -> Unit,
    onCharactersSeeAllClicked: () -> Unit
) {
    val animeDetailsState: AnimeDetailsScreenUiState
        by animeViewModel.animeDetails.collectAsStateWithLifecycle()

    AnimeDetailsScreen(
        modifier = modifier,
        animeDetailsState = animeDetailsState,
        onCharacterClicked = onCharacterClicked,
        onSaveButtonClicked = {},
        onAnimeClicked = onAnimeClicked,
        onCharactersSeeAllClicked = onCharactersSeeAllClicked
    )
}

@Composable
fun AnimeDetailsScreen(
    modifier: Modifier = Modifier,
    animeDetailsState: AnimeDetailsScreenUiState,
    onCharacterClicked: (Int) -> Unit,
    onSaveButtonClicked: () -> Unit,
    onAnimeClicked: (Int) -> Unit,
    onCharactersSeeAllClicked: () -> Unit
) {
    when (animeDetailsState) {
        is AnimeDetailsScreenUiState.AnimeDetails -> {
            AnimeDetailsContent(
                modifier = modifier,
                anime = animeDetailsState.animeDetails,
                onCharacterClicked = onCharacterClicked,
                onSaveButtonClicked = onSaveButtonClicked,
                onAnimeClicked = onAnimeClicked,
                onCharactersSeeAllClicked = onCharactersSeeAllClicked
            )
        }

        is AnimeDetailsScreenUiState.Error -> {
            TODO()
        }

        is AnimeDetailsScreenUiState.Loading -> {
            AnimeDetailsScreenLoadingComponent()
        }
    }
}

@Composable
fun AnimeDetailsContent(
    modifier: Modifier,
    anime: Anime,
    onCharacterClicked: (Int) -> Unit,
    onSaveButtonClicked: () -> Unit,
    onAnimeClicked: (Int) -> Unit,
    onCharactersSeeAllClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(color = AnimeTheme.colors.background)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = AnimeTheme.space.space48dp),
        verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space24dp)
    ) {
        AnimeDetailsHeader(
            modifier = Modifier,
            anime = anime
        )
        AnimeDescAndCTA(
            modifier = Modifier.padding(horizontal = AnimeTheme.space.space20dp),
            animeDesc = anime.description,
            onSaveButtonClicked = onSaveButtonClicked
        )
        AnimeCharacters(
            modifier = Modifier.padding(horizontal = AnimeTheme.space.space20dp),
            onCharacterClicked = onCharacterClicked,
            passedCharacters = anime.characters,
            onCharactersSeeAllClicked = onCharactersSeeAllClicked
        )
        AnimeRecommendations(
            modifier = Modifier.padding(horizontal = AnimeTheme.space.space20dp),
            recommendedAnimes = anime.recommendations,
            onAnimeClicked = onAnimeClicked
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
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

            FlowRow(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(AnimeTheme.space.space8dp),
                verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space8dp)
            ) {
                anime.genres?.let { genres ->
                    genres.forEach {
                        it?.let { genre ->
                            Badge(text = genre)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnimeDescAndCTA(
    modifier: Modifier,
    animeDesc: String?,
    onSaveButtonClicked: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space20dp)
    ) {
        // TODO think of how to show see more on text that are long
        animeDesc?.let {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = it,
                color = AnimeTheme.colors.textNeutral,
                style = AnimeTheme.typography.bodyMedium,
                textAlign = TextAlign.Start
            )
        }

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onSaveButtonClicked
        ) {
            Text(
                text = stringResource(id = com.mumbicodes.anime.R.string.addToList),
                style = AnimeTheme.typography.bodyMediumBold
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AnimeRecommendations(
    modifier: Modifier,
    recommendedAnimes: List<Anime?>?,
    onAnimeClicked: (Int) -> Unit
) {
    recommendedAnimes?.let {
        val animes = it.toSet()
        if (animes.isNotEmpty()) {
            // TODO hide the see all button if the list is short and
            AnimeSection(
                modifier = modifier,
                sectionTitle = com.mumbicodes.anime.R.string.recommendationsSectionTitle,
                buttonText = com.mumbicodes.anime.R.string.seeAll,
                buttonOnClick = { /*TODO*/ }
            ) {
                FlowColumn(
                    modifier = Modifier.padding(horizontal = AnimeTheme.space.space20dp),
                    verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space16dp)
                ) {
                    animes.take(3).forEach { recommendedAnime ->
                        recommendedAnime?.let { anime ->
                            HorizontalAnimeComponent(
                                onClick = { onAnimeClicked(anime.id) },
                                coverImageUrl = anime.coverImage,
                                animeTitle = anime.title?.english ?: anime.title?.romaji
                                    ?: anime.title?.native ?: "",
                                animeDescription = anime.description ?: "",
                                numberOfEpisodes = anime.episodes ?: 0,
                                episodeDuration = anime.duration ?: 0
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnimeCharacters(
    modifier: Modifier,
    passedCharacters: List<Character?>?,
    onCharacterClicked: (Int) -> Unit,
    onCharactersSeeAllClicked: () -> Unit
) {
    passedCharacters?.let { characters ->

        AnimeSection(
            modifier = modifier,
            sectionTitle = com.mumbicodes.anime.R.string.animeCharacters,
            buttonText = com.mumbicodes.anime.R.string.seeAll,
            buttonOnClick = onCharactersSeeAllClicked
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(AnimeTheme.space.space8dp),
                contentPadding = PaddingValues(horizontal = AnimeTheme.space.space20dp)
            ) {
                items(characters) {
                    it?.let { character ->
                        VerticalCharacterComponent(
                            onClick = {
                                onCharacterClicked(character.id)
                            },
                            coverImageUrl = character.image,
                            characterName = character.name?.full ?: character.name?.native
                                ?: "No name"
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AnimeDetailsHeaderPreview() {
    AnimeListTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            backgroundColor = AnimeTheme.colors.background
        ) {
            AnimeDetailsHeader(
                anime = Anime(
                    id = 1,
                    title = AnimeTitle(english = "Anime Title", native = null, romaji = null)
                )
            )
        }
    }
}