package com.mumbicodes.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mumbicodes.designsystem.components.HorizontalAnimeComponent
import com.mumbicodes.designsystem.components.ListLoadingComponent
import com.mumbicodes.designsystem.components.TextTopBarComponent
import com.mumbicodes.designsystem.theme.AnimeTheme
import java.util.Locale

@Composable
fun AllCategoriesScreenRoute(
    modifier: Modifier = Modifier,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    onAnimeClicked: (Int) -> Unit,
    onBackButtonClicked: () -> Unit = {}
) {
    val animeSortType by homeScreenViewModel.animeSortType.collectAsStateWithLifecycle()

    // TODO - research whether to collect all flows
    val recommendedAnimeUiStates: RecommendedAnimesUiStates
        by homeScreenViewModel.recommendedUiState.collectAsStateWithLifecycle()
    val popularAnimeUiStates: PopularAnimeStates by homeScreenViewModel.popularUiState.collectAsStateWithLifecycle()
    val trendingAnimeUiStates: TrendingAnimeStates by homeScreenViewModel.trendingUiState.collectAsStateWithLifecycle()

    AllCategoriesScreen(
        modifier = modifier,
        animeSortType = animeSortType,
        recommendedAnimeUiStates = recommendedAnimeUiStates,
        popularAnimeUiStates = popularAnimeUiStates,
        trendingAnimeUiStates = trendingAnimeUiStates,
        onAnimeClicked = onAnimeClicked,
        onBackButtonClicked = onBackButtonClicked
    )

    /*//TODO Add a top bar
    Text(
        text = "This is the categories screen",
        modifier = Modifier.clickable {
            onAnimeClicked()
        }
    )*/
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AllCategoriesScreen(
    modifier: Modifier = Modifier,
    animeSortType: AnimeSortType,
    recommendedAnimeUiStates: RecommendedAnimesUiStates,
    popularAnimeUiStates: PopularAnimeStates,
    trendingAnimeUiStates: TrendingAnimeStates,
    onAnimeClicked: (Int) -> Unit,
    onBackButtonClicked: () -> Unit
) {
    when (animeSortType) {
        AnimeSortType.RECOMMENDED -> {
            when (recommendedAnimeUiStates) {
                is RecommendedAnimesUiStates.Error -> TODO()
                is RecommendedAnimesUiStates.Loading -> {
                    ListLoadingComponent()
                }

                is RecommendedAnimesUiStates.RecommendedAnimes -> {
                    LazyColumn(
                        modifier = Modifier
                            .background(color = AnimeTheme.colors.background)
                            .padding(horizontal = AnimeTheme.space.space20dp),
                        verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space16dp)
                    ) {
                        stickyHeader {
                            TextTopBarComponent(
                                onBackButtonClicked = {
                                    onBackButtonClicked()
                                },
                                headingText = animeSortType.name.lowercase(Locale.ROOT)
                                    .replaceFirstChar {
                                        if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                                    }
                            )
                        }
                        items(recommendedAnimeUiStates.recommended) { anime ->
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

        AnimeSortType.TRENDING -> {
            when (trendingAnimeUiStates) {
                is TrendingAnimeStates.Error -> TODO()
                TrendingAnimeStates.Loading -> {
                    ListLoadingComponent()
                }

                is TrendingAnimeStates.TrendingAnimes -> {
                    LazyColumn(
                        modifier = Modifier
                            .background(color = AnimeTheme.colors.background)
                            .padding(horizontal = AnimeTheme.space.space20dp),
                        verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space16dp)
                    ) {
                        stickyHeader {
                            TextTopBarComponent(
                                onBackButtonClicked = {
                                    onBackButtonClicked()
                                },
                                headingText = animeSortType.name.lowercase(Locale.ROOT)
                                    .replaceFirstChar {
                                        if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                                    }
                            )
                        }
                        items(trendingAnimeUiStates.trending) { anime ->
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

        AnimeSortType.POPULAR -> {
            when (popularAnimeUiStates) {
                is PopularAnimeStates.Error -> TODO()
                PopularAnimeStates.Loading -> {
                    ListLoadingComponent()
                }

                is PopularAnimeStates.PopularAnimes -> {
                    LazyColumn(
                        modifier = Modifier
                            .background(color = AnimeTheme.colors.background)
                            .padding(horizontal = AnimeTheme.space.space20dp),
                        verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space16dp)
                    ) {
                        stickyHeader {
                            TextTopBarComponent(
                                onBackButtonClicked = {
                                    onBackButtonClicked()
                                },
                                headingText = animeSortType.name.lowercase(Locale.ROOT)
                                    .replaceFirstChar {
                                        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                                    }
                            )
                        }
                        items(popularAnimeUiStates.popular) { anime ->
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