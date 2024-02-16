package com.mumbicodes.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mumbicodes.designsystem.components.ErrorBannerComponent
import com.mumbicodes.designsystem.components.HorizontalAnimeComponent
import com.mumbicodes.designsystem.components.ListLoadingComponent
import com.mumbicodes.designsystem.components.PagedAnimeComponent
import com.mumbicodes.designsystem.components.SelectedButton
import com.mumbicodes.designsystem.components.TextTopBarComponent
import com.mumbicodes.designsystem.theme.AnimeTheme
import com.mumbicodes.model.data.Anime
import java.util.Locale
import kotlin.math.absoluteValue

@Composable
fun AllCategoriesScreenRoute(
    modifier: Modifier = Modifier,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    onAnimeClicked: (Int) -> Unit,
    onBackButtonClicked: () -> Unit = {}
) {
    // TODO - research whether to have a separate all categories screen state

    val screenState: HomeScreenState by homeScreenViewModel.homeState.collectAsStateWithLifecycle()

    AllCategoriesScreen(
        modifier = modifier,
        screenState = screenState,
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
    screenState: HomeScreenState,
    onAnimeClicked: (Int) -> Unit,
    onBackButtonClicked: () -> Unit,
    onLayoutButtonClicked: (SelectedLayoutType) -> Unit = {}
) {
    when (screenState.animeSortType) {
        AnimeSortType.RECOMMENDED -> {
            when (screenState.recommendedAnimesUiStates) {
                is RecommendedAnimesUiStates.Error -> {
                    ErrorBannerComponent(errorMessage = screenState.recommendedAnimesUiStates.errorMessage)
                }
                is RecommendedAnimesUiStates.Loading -> {
                    ListLoadingComponent()
                }

                is RecommendedAnimesUiStates.RecommendedAnimes -> {
                    CategorizedAnimeContent(
                        modifier = modifier,
                        animeList = screenState.recommendedAnimesUiStates.recommended,
                        animeSortType = screenState.animeSortType,
                        selectedLayoutType = screenState.selectedLayoutType,
                        onAnimeClicked = onAnimeClicked,
                        onBackButtonClicked = onBackButtonClicked,
                        onLayoutButtonClicked = onLayoutButtonClicked
                    )
                }
            }
        }

        AnimeSortType.TRENDING -> {
            when (screenState.trendingAnimesUiState) {
                is TrendingAnimeStates.Error -> {
                    ErrorBannerComponent(errorMessage = screenState.trendingAnimesUiState.errorMessage)
                }
                TrendingAnimeStates.Loading -> {
                    ListLoadingComponent()
                }

                is TrendingAnimeStates.TrendingAnimes -> {
                    CategorizedAnimeContent(
                        modifier = modifier,
                        animeList = screenState.trendingAnimesUiState.trending,
                        animeSortType = screenState.animeSortType,
                        selectedLayoutType = screenState.selectedLayoutType,
                        onAnimeClicked = onAnimeClicked,
                        onBackButtonClicked = onBackButtonClicked,
                        onLayoutButtonClicked = onLayoutButtonClicked
                    )
                }
            }
        }

        AnimeSortType.POPULAR -> {
            when (screenState.popularAnimesUiState) {
                is PopularAnimeStates.Error -> {
                    ErrorBannerComponent(errorMessage = screenState.popularAnimesUiState.errorMessage)
                }
                PopularAnimeStates.Loading -> {
                    ListLoadingComponent()
                }

                is PopularAnimeStates.PopularAnimes -> {
                    CategorizedAnimeContent(
                        modifier = modifier,
                        animeList = screenState.popularAnimesUiState.popular,
                        animeSortType = screenState.animeSortType,
                        selectedLayoutType = screenState.selectedLayoutType,
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
    Column(
        modifier = modifier
            .background(color = AnimeTheme.colors.background),
        verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space16dp)
    ) {
        TextTopBarComponent(
            modifier = modifier
                .padding(horizontal = AnimeTheme.space.space20dp),
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
        when (selectedLayoutType) {
            SelectedLayoutType.LIST -> {
                LazyColumn(
                    modifier = modifier
                        .background(color = AnimeTheme.colors.background)
                        .padding(horizontal = AnimeTheme.space.space20dp),
                    verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space16dp)
                ) {
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

            SelectedLayoutType.GRID -> {
                val pagerState = rememberPagerState(pageCount = {
                    animeList.size
                })

                HorizontalPager(
                    state = pagerState,
                    contentPadding = PaddingValues(
                        horizontal = AnimeTheme.space.space48dp
                    ),
                    pageSpacing = AnimeTheme.space.space16dp
                ) { page ->

                    // TODO research how to minimize the size of the previous and next anime
                    PagedAnimeComponent(
                        modifier = Modifier
                            .graphicsLayer {
                                // Calculate the absolute offset for the current page from the
                                // scroll position. We use the absolute value which allows us to mirror
                                // any effects for both directions
                                val pageOffset = (
                                    (pagerState.currentPage - page) + pagerState
                                        .currentPageOffsetFraction
                                    ).absoluteValue

                                // We animate the alpha, between 50% and 100%
                                alpha = lerp(
                                    start = 0.5f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                            },
                        onAnimeClicked = { onAnimeClicked(animeList[page].id) },
                        coverImageUrl = animeList[page].coverImage,
                        animeTitle = animeList[page].title?.english ?: animeList[page].title?.romaji
                            ?: animeList[page].title?.native ?: "",
                        numberOfEpisodes = animeList[page].episodes ?: 0,
                        episodeDuration = animeList[page].duration ?: 0
                    )
                }
            }
        }
    }
}