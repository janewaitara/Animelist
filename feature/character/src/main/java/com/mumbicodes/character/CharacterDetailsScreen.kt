package com.mumbicodes.character

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mumbicodes.designsystem.atoms.Badge
import com.mumbicodes.designsystem.atoms.BadgeColor
import com.mumbicodes.designsystem.atoms.CircleDecoration
import com.mumbicodes.designsystem.atoms.Image
import com.mumbicodes.designsystem.atoms.Text
import com.mumbicodes.designsystem.components.HorizontalAnimeComponent
import com.mumbicodes.designsystem.theme.AnimeTheme
import com.mumbicodes.model.data.Character

@Composable
fun CharacterDetailsScreenRoute(
    modifier: Modifier = Modifier,
    characterViewModel: CharacterViewModel = hiltViewModel()
) {
    val characterDetails by characterViewModel.characterUiState.collectAsStateWithLifecycle()

    CharacterDetailsScreen(characterDetailsState = characterDetails)
}

@Composable
fun CharacterDetailsScreen(
    modifier: Modifier = Modifier,
    characterDetailsState: CharacterScreenUiState
) {
    when (characterDetailsState) {
        is CharacterScreenUiState.CharacterDetails -> {
            AnimeDetailsContent(
                modifier = modifier,
                characterDetails = characterDetailsState.data
            )
        }

        is CharacterScreenUiState.Error -> {
        }

        CharacterScreenUiState.Loading -> {
            CharacterDetailsLoadingComponent()
        }
    }
}

@Composable
fun AnimeDetailsContent(
    modifier: Modifier = Modifier,
    characterDetails: Character
) {
    Column(
        modifier = modifier
            .background(color = AnimeTheme.colors.background)
            .fillMaxHeight(0.9f)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space24dp)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(214.dp),
            contentDescription = "${characterDetails.name?.full} cover image",
            coverImageUrl = characterDetails.image,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = modifier
                .padding(horizontal = AnimeTheme.space.space20dp),
            verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space24dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space8dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(AnimeTheme.space.space16dp)
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = characterDetails.name?.full ?: "",
                        style = AnimeTheme.typography.titleSmall,
                        color = AnimeTheme.colors.textStrong
                    )
                    characterDetails.gender?.let {
                        Badge(
                            text = it,
                            badgeColor = when (it) {
                                "Male" -> BadgeColor.Info
                                "Female" -> BadgeColor.Primary
                                else -> BadgeColor.Info
                            }
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(AnimeTheme.space.space8dp)
                ) {
                    Text(
                        modifier = Modifier,
                        text = characterDetails.name?.native ?: "",
                        style = AnimeTheme.typography.bodyMediumBold,
                        color = AnimeTheme.colors.primary
                    )

                    characterDetails.age?.let {
                        CircleDecoration()

                        Text(
                            modifier = Modifier,
                            text = it,
                            style = AnimeTheme.typography.bodyMedium,
                            color = AnimeTheme.colors.textStrong
                        )
                    }
                }
            }

            characterDetails.description?.let {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = it,
                    color = AnimeTheme.colors.textNeutral,
                    style = AnimeTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
            }

            characterDetails.animes?.let { animes ->

                if (animes.isNotEmpty()) {
                    Column {
                        Text(
                            modifier = Modifier,
                            text = stringResource(id = R.string.otherAnimes),
                            style = AnimeTheme.typography.bodyLargeBold,
                            color = AnimeTheme.colors.textStrong
                        )
                        Spacer(modifier = Modifier.height(AnimeTheme.space.space16dp))

                        animes.forEach {
                            it?.let {
                                HorizontalAnimeComponent(
                                    onClick = { },
                                    coverImageUrl = it.coverImage,
                                    animeTitle = it.title?.english ?: it.title?.romaji
                                        ?: it.title?.native ?: "",
                                    animeDescription = it.description ?: "",
                                    numberOfEpisodes = it.episodes ?: 0,
                                    episodeDuration = it.duration ?: 0
                                )
                                Spacer(modifier = Modifier.height(AnimeTheme.space.space8dp))
                            }
                        }
                    }
                }
            }
        }
    }
}