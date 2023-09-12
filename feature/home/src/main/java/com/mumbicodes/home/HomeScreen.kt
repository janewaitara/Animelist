package com.mumbicodes.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mumbicodes.designsystem.atoms.Image
import com.mumbicodes.designsystem.molecules.AnimeSection
import com.mumbicodes.designsystem.theme.AnimeTheme
import com.mumbicodes.home.components.VerticalAnimeComponent

@Composable
fun HomeScreenRoute(
    modifier: Modifier = Modifier,
    onAnimeClicked: (Int) -> Unit,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    onSeeAllButtonClicked: () -> Unit
) {
    val recommendedAnimeUiStates: RecommendedAnimesUiStates
        by homeScreenViewModel.recommendedUiState.collectAsStateWithLifecycle()
    val popularAnimeUiStates: PopularAnimeStates by homeScreenViewModel.popularUiState.collectAsStateWithLifecycle()
    val trendingAnimeUiStates: TrendingAnimeStates by homeScreenViewModel.trendingUiState.collectAsStateWithLifecycle()

    HomeScreen(
        modifier = modifier,
        recommendedAnimeUiStates = recommendedAnimeUiStates,
        popularAnimeUiStates = popularAnimeUiStates,
        trendingAnimeUiStates = trendingAnimeUiStates,
        onAnimeClicked = onAnimeClicked,
        onSeeAllButtonClicked = {
            homeScreenViewModel.updateAnimeSortType(it)
            onSeeAllButtonClicked()
        }
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    recommendedAnimeUiStates: RecommendedAnimesUiStates,
    popularAnimeUiStates: PopularAnimeStates,
    trendingAnimeUiStates: TrendingAnimeStates,
    onAnimeClicked: (Int) -> Unit,
    onSeeAllButtonClicked: (AnimeSortType) -> Unit
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .background(color = AnimeTheme.colors.background),
        verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space32dp)
    ) {
        // TODO which image is this coming from:
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(214.dp),
            coverImageUrl = null
        )

        // Trending section
        when (trendingAnimeUiStates) {
            is TrendingAnimeStates.TrendingAnimes -> {
                AnimeSection(
                    modifier = Modifier.padding(horizontal = AnimeTheme.space.space20dp),
                    sectionTitle = R.string.trending,
                    buttonText = R.string.seeAll,
                    buttonOnClick = { onSeeAllButtonClicked(AnimeSortType.TRENDING) }
                ) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(AnimeTheme.space.space12dp),
                        contentPadding = PaddingValues(horizontal = AnimeTheme.space.space20dp)
                    ) {
                        items(trendingAnimeUiStates.trending) { anime ->
                            VerticalAnimeComponent(
                                onClick = { onAnimeClicked(anime.id) },
                                coverImageUrl = anime.coverImage,
                                animeTitle = anime.title?.english ?: anime.title?.romaji
                                    ?: anime.title?.native ?: ""
                            )
                        }
                    }
                }
            }

            is TrendingAnimeStates.Error -> {
                // TODO Create an error banner
                Text(
                    text = trendingAnimeUiStates.errorMessage,
                    modifier = Modifier.clickable {
                    }
                )
            }

            TrendingAnimeStates.Loading -> {
                // TODO Create a loading skeleton
                Text(
                    text = "This is the loading state",
                    modifier = Modifier.clickable {
                    }
                )
            }
        }

        // Recommended section
        when (recommendedAnimeUiStates) {
            is RecommendedAnimesUiStates.RecommendedAnimes -> {
                AnimeSection(
                    modifier = Modifier.padding(horizontal = AnimeTheme.space.space20dp),
                    sectionTitle = R.string.recommended,
                    buttonText = R.string.seeAll,
                    buttonOnClick = { onSeeAllButtonClicked(AnimeSortType.RECOMMENDED) }
                ) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(AnimeTheme.space.space12dp),
                        contentPadding = PaddingValues(horizontal = AnimeTheme.space.space20dp)
                    ) {
                        items(recommendedAnimeUiStates.recommended) { anime ->
                            VerticalAnimeComponent(
                                onClick = { onAnimeClicked(anime.id) },
                                coverImageUrl = anime.coverImage,
                                animeTitle = anime.title?.english ?: anime.title?.romaji
                                    ?: anime.title?.native ?: ""
                            )
                        }
                    }
                }
            }

            is RecommendedAnimesUiStates.Error -> {
                // TODO Create an error banner
                Text(
                    text = (recommendedAnimeUiStates as RecommendedAnimesUiStates.Error).errorMessage,
                    modifier = Modifier.clickable {
                    }
                )
            }

            RecommendedAnimesUiStates.Loading -> {
                // TODO Create a loading skeleton
                Text(
                    text = "This is the loading state",
                    modifier = Modifier.clickable {
                    }
                )
            }
        }

        // Popular section
        when (popularAnimeUiStates) {
            is PopularAnimeStates.PopularAnimes -> {
                AnimeSection(
                    modifier = Modifier.padding(horizontal = AnimeTheme.space.space20dp),
                    sectionTitle = R.string.popular,
                    buttonText = R.string.seeAll,
                    buttonOnClick = { onSeeAllButtonClicked(AnimeSortType.POPULAR) }
                ) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(AnimeTheme.space.space12dp),
                        contentPadding = PaddingValues(horizontal = AnimeTheme.space.space20dp)
                    ) {
                        items(popularAnimeUiStates.popular) { anime ->
                            VerticalAnimeComponent(
                                onClick = { onAnimeClicked(anime.id) },
                                coverImageUrl = anime.coverImage,
                                animeTitle = anime.title?.english ?: anime.title?.romaji
                                    ?: anime.title?.native ?: ""
                            )
                        }
                    }
                }
            }

            is PopularAnimeStates.Error -> {
                // TODO Create an error banner
                Text(
                    text = popularAnimeUiStates.errorMessage,
                    modifier = Modifier.clickable {
                    }
                )
            }

            PopularAnimeStates.Loading -> {
                // TODO Create a loading skeleton
                Text(
                    text = "This is the loading state for popular",
                    modifier = Modifier.clickable {
                    }
                )
            }
        }
    }
}