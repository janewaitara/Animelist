package com.mumbicodes.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onAnimeClicked: () -> Unit,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {
    val recommendedAnimesUiStates: RecommendedAnimesUiStates
        by homeScreenViewModel.recommendedUiState.collectAsStateWithLifecycle()
    val popularAnimeUiStates: PopularAnimeStates by homeScreenViewModel.popularUiState.collectAsStateWithLifecycle()
    val trendingAnimeUiStates: TrendingAnimeStates by homeScreenViewModel.trendingUiState.collectAsStateWithLifecycle()
    val emptyString = "It is empty"
    Column() {
        when (recommendedAnimesUiStates) {
            is RecommendedAnimesUiStates.RecommendedAnimes -> {
                Column() {
                    Text(
                        text = "Recommended",
                        modifier = modifier.clickable {
                            onAnimeClicked()
                        }
                    )
                    Text(
                        text = (recommendedAnimesUiStates as RecommendedAnimesUiStates.RecommendedAnimes)
                            .recommended.first().media?.title?.english
                            ?: emptyString,
                        modifier = modifier.clickable {
                            onAnimeClicked()
                        }
                    )

                    Spacer(modifier = Modifier.height(48.dp))
                }
            }

            is RecommendedAnimesUiStates.Error -> {
                Text(
                    text = (recommendedAnimesUiStates as RecommendedAnimesUiStates.Error).errorMessage,
                    modifier = modifier.clickable {
                        onAnimeClicked()
                    }
                )
            }

            RecommendedAnimesUiStates.Loading -> {
                Text(
                    text = "This is the loading state",
                    modifier = modifier.clickable {
                        onAnimeClicked()
                    }
                )
            }
        }

        when (popularAnimeUiStates) {
            is PopularAnimeStates.PopularAnimes -> {
                Column() {
                    Spacer(modifier = Modifier.height(48.dp))

                    Text(
                        text = "Popular:",
                        modifier = modifier.clickable {
                            onAnimeClicked()
                        }
                    )
                    Text(
                        text = (popularAnimeUiStates as PopularAnimeStates.PopularAnimes)
                            .popular.first().title?.english
                            ?: emptyString,
                        modifier = modifier.clickable {
                            onAnimeClicked()
                        }
                    )
                }
            }

            is PopularAnimeStates.Error -> {
                Text(
                    text = (popularAnimeUiStates as PopularAnimeStates.Error).errorMessage,
                    modifier = modifier.clickable {
                        onAnimeClicked()
                    }
                )
            }

            PopularAnimeStates.Loading -> {
                Text(
                    text = "This is the loading popular state",
                    modifier = modifier.clickable {
                        onAnimeClicked()
                    }
                )
            }
        }

        when (trendingAnimeUiStates) {
            is TrendingAnimeStates.TrendingAnimes -> {
                Column() {
                    Spacer(modifier = Modifier.height(48.dp))

                    Text(
                        text = "Trending:",
                        modifier = modifier.clickable {
                            onAnimeClicked()
                        }
                    )
                    Text(
                        text = (trendingAnimeUiStates as TrendingAnimeStates.TrendingAnimes)
                            .trending.first().title?.english
                            ?: emptyString,
                        modifier = modifier.clickable {
                            onAnimeClicked()
                        }
                    )
                }
            }

            is TrendingAnimeStates.Error -> {
                Text(
                    text = (trendingAnimeUiStates as TrendingAnimeStates.Error).errorMessage,
                    modifier = modifier.clickable {
                        onAnimeClicked()
                    }
                )
            }

            TrendingAnimeStates.Loading -> {
                Text(
                    text = "This is the loading trending state",
                    modifier = modifier.clickable {
                        onAnimeClicked()
                    }
                )
            }
        }
    }
}