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
import com.mumbicodes.designsystem.components.SelectedButton
import com.mumbicodes.designsystem.components.TextTopBarComponent
import com.mumbicodes.designsystem.theme.AnimeTheme
import com.mumbicodes.model.data.Anime
import java.util.Locale

@Composable
fun AllCategoriesScreenRoute(
    modifier: Modifier = Modifier,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    onAnimeClicked: (Int) -> Unit,
    onBackButtonClicked: () -> Unit = {}
) {
    val animeSortType by homeScreenViewModel.animeSortType.collectAsStateWithLifecycle()
    val selectedLayoutType by homeScreenViewModel.selectedLayout.collectAsStateWithLifecycle()

    // TODO - research whether to collect all flows
    val recommendedAnimeUiStates: RecommendedAnimesUiStates
        by homeScreenViewModel.recommendedUiState.collectAsStateWithLifecycle()
    val popularAnimeUiStates: PopularAnimeStates by homeScreenViewModel.popularUiState.collectAsStateWithLifecycle()
    val trendingAnimeUiStates: TrendingAnimeStates by homeScreenViewModel.trendingUiState.collectAsStateWithLifecycle()

    AllCategoriesScreen(
        modifier = modifier,
        animeSortType = animeSortType,
        selectedLayoutType = selectedLayoutType,
        recommendedAnimeUiStates = recommendedAnimeUiStates,
        popularAnimeUiStates = popularAnimeUiStates,
        trendingAnimeUiStates = trendingAnimeUiStates,
        onAnimeClicked = onAnimeClicked,
        onBackButtonClicked = onBackButtonClicked,
        onLayoutButtonClicked = {
            homeScreenViewModel.updateSelectedLayoutType(it)
        }
    )
}

@Composable
fun AllCategoriesScreen(
    modifier: Modifier = Modifier,
    animeSortType: AnimeSortType,
    selectedLayoutType: SelectedLayoutType,
    recommendedAnimeUiStates: RecommendedAnimesUiStates,
    popularAnimeUiStates: PopularAnimeStates,
    trendingAnimeUiStates: TrendingAnimeStates,
    onAnimeClicked: (Int) -> Unit,
    onBackButtonClicked: () -> Unit,
    onLayoutButtonClicked: (SelectedLayoutType) -> Unit = {}
) {
    when (animeSortType) {
        AnimeSortType.RECOMMENDED -> {
            when (recommendedAnimeUiStates) {
                is RecommendedAnimesUiStates.Error -> TODO()
                is RecommendedAnimesUiStates.Loading -> {
                    ListLoadingComponent()
                }

                is RecommendedAnimesUiStates.RecommendedAnimes -> {
                    CategorizedAnimeContent(
                        modifier = modifier,
                        animeList = recommendedAnimeUiStates.recommended,
                        animeSortType = animeSortType,
                        selectedLayoutType = selectedLayoutType,
                        onAnimeClicked = onAnimeClicked,
                        onBackButtonClicked = onBackButtonClicked,
                        onLayoutButtonClicked = onLayoutButtonClicked
                    )
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
                    CategorizedAnimeContent(
                        modifier = modifier,
                        animeList = trendingAnimeUiStates.trending,
                        animeSortType = animeSortType,
                        selectedLayoutType = selectedLayoutType,
                        onAnimeClicked = onAnimeClicked,
                        onBackButtonClicked = onBackButtonClicked,
                        onLayoutButtonClicked = onLayoutButtonClicked
                    )
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
                    CategorizedAnimeContent(
                        modifier = modifier,
                        animeList = popularAnimeUiStates.popular,
                        animeSortType = animeSortType,
                        selectedLayoutType = selectedLayoutType,
                        onAnimeClicked = onAnimeClicked,
                        onBackButtonClicked = onBackButtonClicked,
                        onLayoutButtonClicked = onLayoutButtonClicked
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategorizedAnimeContent(
    modifier: Modifier = Modifier,
    animeList: List<Anime>,
    animeSortType: AnimeSortType,
    selectedLayoutType: SelectedLayoutType,
    onAnimeClicked: (Int) -> Unit,
    onBackButtonClicked: () -> Unit,
    onLayoutButtonClicked: (SelectedLayoutType) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
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
                    },
                selectedLayoutType = when (selectedLayoutType) {
                    SelectedLayoutType.LIST -> SelectedButton.LIST
                    SelectedLayoutType.GRID -> SelectedButton.GRID
                },
                onLayoutButtonClicked = { selectedButton ->
                    onLayoutButtonClicked(
                        when (selectedButton) {
                            SelectedButton.LIST -> SelectedLayoutType.LIST
                            SelectedButton.GRID -> SelectedLayoutType.GRID
                        }
                    )
                }
            )
        }
        items(animeList) { anime ->
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