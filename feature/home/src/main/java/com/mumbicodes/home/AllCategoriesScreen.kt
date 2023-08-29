package com.mumbicodes.home

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
import com.mumbicodes.designsystem.theme.AnimeTheme

@Composable
fun AllCategoriesScreenRoute(
    modifier: Modifier = Modifier,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    onAnimeClicked: () -> Unit,
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
        onBackButtonClicked = {}
    )

    /*//TODO Add a top bar
    Text(
        text = "This is the categories screen",
        modifier = Modifier.clickable {
            onAnimeClicked()
        }
    )*/
}

@Composable
fun AllCategoriesScreen(
    modifier: Modifier = Modifier,
    animeSortType: AnimeSortType,
    recommendedAnimeUiStates: RecommendedAnimesUiStates,
    popularAnimeUiStates: PopularAnimeStates,
    trendingAnimeUiStates: TrendingAnimeStates,
    onAnimeClicked: () -> Unit,
    onBackButtonClicked: () -> Unit
) {
    when (animeSortType) {
        AnimeSortType.RECOMMENDED -> {
            when (recommendedAnimeUiStates) {
                is RecommendedAnimesUiStates.Error -> TODO()
                is RecommendedAnimesUiStates.Loading -> TODO()
                is RecommendedAnimesUiStates.RecommendedAnimes -> {
                    LazyColumn(
                        modifier = Modifier.padding(horizontal = AnimeTheme.space.space20dp),
                        verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space16dp)
                    ) {
                        items(recommendedAnimeUiStates.recommended) { anime ->
                            HorizontalAnimeComponent(
                                onClick = onAnimeClicked,
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
                TrendingAnimeStates.Loading -> TODO()
                is TrendingAnimeStates.TrendingAnimes -> {
                    LazyColumn(
                        modifier = Modifier.padding(horizontal = AnimeTheme.space.space20dp),
                        verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space16dp)
                    ) {
                        items(trendingAnimeUiStates.trending) { anime ->
                            HorizontalAnimeComponent(
                                onClick = onAnimeClicked,
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
                PopularAnimeStates.Loading -> TODO()
                is PopularAnimeStates.PopularAnimes -> {
                    LazyColumn(
                        modifier = Modifier.padding(horizontal = AnimeTheme.space.space20dp),
                        verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space16dp)
                    ) {
                        items(popularAnimeUiStates.popular) { anime ->
                            HorizontalAnimeComponent(
                                onClick = onAnimeClicked,
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