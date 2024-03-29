package com.mumbicodes.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mumbicodes.designsystem.atoms.Icon
import com.mumbicodes.designsystem.atoms.IconButton
import com.mumbicodes.designsystem.atoms.IconButtonColors
import com.mumbicodes.designsystem.atoms.Image
import com.mumbicodes.designsystem.components.ErrorBannerComponent
import com.mumbicodes.designsystem.components.HorizontalListLoading
import com.mumbicodes.designsystem.components.swipeToDismiss
import com.mumbicodes.designsystem.icons.AnimeListIcons
import com.mumbicodes.designsystem.molecules.AnimeSection
import com.mumbicodes.designsystem.theme.AnimeTheme
import com.mumbicodes.designsystem.theme.Background_dark
import com.mumbicodes.designsystem.theme.Background_light
import com.mumbicodes.home.components.VerticalAnimeComponent
import com.mumbicodes.model.data.Anime
import com.mumbicodes.ui.components.TrailerComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreenRoute(
    modifier: Modifier = Modifier,
    onAnimeClicked: (Int) -> Unit,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    onSeeAllButtonClicked: () -> Unit
) {
    val homeScreenState: HomeScreenState by homeScreenViewModel.homeState.collectAsStateWithLifecycle()

    HomeScreen(
        modifier = modifier,
        homeScreenState = homeScreenState,
        onAnimeClicked = { animeId ->
            homeScreenViewModel.saveAnimeTrailerPosition()
            onAnimeClicked(animeId)
        },
        onSeeAllButtonClicked = {
            homeScreenViewModel.updateAnimeSortType(it)
            onSeeAllButtonClicked()
        },
        onToggleAudioClicked = homeScreenViewModel::toggleAudioStateVideo,
        onPlayPauseClicked = homeScreenViewModel::onPlayPauseClicked,
        onReplayVideoClicked = homeScreenViewModel::replayVideo,
        onTrendingAnimeSwiped = homeScreenViewModel::updateTrendingListOnSwipe
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeScreenState: HomeScreenState,
    onAnimeClicked: (Int) -> Unit,
    onSeeAllButtonClicked: (AnimeSortType) -> Unit,
    onToggleAudioClicked: () -> Unit,
    onPlayPauseClicked: () -> Unit,
    onReplayVideoClicked: () -> Unit,
    onTrendingAnimeSwiped: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .background(color = AnimeTheme.colors.background),
        verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space32dp)
    ) {
        // Trending section
        when (homeScreenState.trendingAnimesUiState) {
            is AnimeUiStates.Success -> {
                TrendingViewPager(
                    trending = homeScreenState.trendingAnimes.take(3),
                    onTrendingAnimeSwiped = onTrendingAnimeSwiped,
                    homeScreenState = homeScreenState,
                    onToggleAudioClicked = onToggleAudioClicked,
                    onPlayPauseClicked = onPlayPauseClicked,
                    onReplayVideoClicked = onReplayVideoClicked,
                    onAnimeClicked = onAnimeClicked
                )
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
                        items(homeScreenState.trendingAnimesUiState.animes) { anime ->
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

            is AnimeUiStates.Error -> {
                ErrorBannerComponent(errorMessage = homeScreenState.trendingAnimesUiState.errorMessage)
            }

            AnimeUiStates.Loading -> {
                HorizontalListLoading()
            }
        }

        // Recommended section
        when (homeScreenState.recommendedAnimesUiStates) {
            is AnimeUiStates.Success -> {
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
                        items(homeScreenState.recommendedAnimesUiStates.animes) { anime ->
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

            is AnimeUiStates.Error -> {
                ErrorBannerComponent(errorMessage = homeScreenState.recommendedAnimesUiStates.errorMessage)
            }

            AnimeUiStates.Loading -> {
                HorizontalListLoading()
            }
        }

        // Popular section
        when (homeScreenState.popularAnimesUiState) {
            is AnimeUiStates.Success -> {
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
                        items(homeScreenState.popularAnimesUiState.animes) { anime ->
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

            is AnimeUiStates.Error -> {
                ErrorBannerComponent(errorMessage = homeScreenState.popularAnimesUiState.errorMessage)
            }

            AnimeUiStates.Loading -> {
                HorizontalListLoading()
            }
        }
        // This is the space after all content has been scrolled that accounts for the bottom Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(color = Color.Transparent)
        )
    }
}

@Composable
fun TrendingViewPager(
    modifier: Modifier = Modifier,
    trending: List<Anime>,
    homeScreenState: HomeScreenState,
    onTrendingAnimeSwiped: (Int) -> Unit,
    onToggleAudioClicked: () -> Unit,
    onPlayPauseClicked: () -> Unit,
    onReplayVideoClicked: () -> Unit,
    onAnimeClicked: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()

    var showVideo by remember {
        mutableStateOf(false)
    }

    // TODO check for internet connectivity in the whole app
    LaunchedEffect(trending.reversed().first()) {
        scope.launch {
            showVideo = false
            delay(5000)
            showVideo = true
        }
    }

    Box(
        modifier = modifier
            .padding(horizontal = AnimeTheme.space.space20dp)
            .height(296.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        trending.reversed().forEachIndexed { index, anime ->
            key(anime) {
                val getXSpacing = {
                    when (index) {
                        trending.reversed().lastIndex -> 0f
                        1 -> 0.1f
                        else -> 0.125f
                    }
                }
                val animatedScaleX by animateFloatAsState(
                    targetValue = 1f - (trending.size - index) * getXSpacing(),
                    label = ""
                )
                val animatedScaleY by animateFloatAsState(
                    targetValue = 1f - (trending.size - index) * 0.05f,
                    label = ""
                )
                val getYSpacing = {
                    when (index) {
                        trending.reversed().lastIndex -> 1
                        1 -> -16
                        else -> -22
                    }
                }
                val animatedYOffset by animateDpAsState(
                    targetValue = ((trending.size - index) * getYSpacing()).dp,
                    label = ""
                )
                Column(
                    modifier = Modifier
                        .offset {
                            IntOffset(x = 0, y = animatedYOffset.roundToPx())
                        }
                        .graphicsLayer {
                            scaleX = animatedScaleX
                            scaleY = animatedScaleY
                        }
                        .clickable {
                            onAnimeClicked(anime.id)
                        }
                        .swipeToDismiss {
                            onTrendingAnimeSwiped(anime.id)
                        }
                        .border(
                            width = 5.dp,
                            shape = AnimeTheme.shapes.mediumShape,
                            color = AnimeTheme.colors.primary.copy(0.3f)
                        )
                        .clip(shape = AnimeTheme.shapes.mediumShape),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    if (trending[index] == trending.last()) {
                        AnimatedVisibility(
                            visible = showVideo.not() || homeScreenState.playerControllerState.videoHasEnded
                        ) {
                            TrailerImage(
                                animeCoverImage = anime.coverImage,
                                videoHasEnded = homeScreenState.playerControllerState.videoHasEnded,
                                onReplayVideoClicked = onReplayVideoClicked
                            )
                        }

                        AnimatedVisibility(
                            visible = showVideo && homeScreenState.playerControllerState.videoHasEnded.not()
                        ) {
                            TrailerComponent(
                                modifier = Modifier,
                                playerControllerState = homeScreenState.playerControllerState,
                                trailerPlayer = homeScreenState.player,
                                onToggleAudioClicked = onToggleAudioClicked,
                                onPlayPauseClicked = onPlayPauseClicked
                            )
                        }
                    } else {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16 / 9f),
                            contentScale = ContentScale.FillBounds,
                            // .height(214.dp),
                            coverImageUrl = anime.coverImage
                        )
                    }
                }
            }
        }
    }
}

/**
 * A box with the trailer image and the replay button which only shows if the video has ended
 * */
@Composable
fun TrailerImage(
    modifier: Modifier = Modifier,
    animeCoverImage: String?,
    videoHasEnded: Boolean,
    onReplayVideoClicked: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f),
            contentScale = ContentScale.FillBounds,
            coverImageUrl = animeCoverImage
        )

        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(AnimeTheme.space.space12dp),
            visible = videoHasEnded
        ) {
            IconButton(
                modifier = Modifier,
                colors = IconButtonColors(
                    containerColor = Background_dark.copy(alpha = 0.75f),
                    contentColor = Background_light,
                    disabledContainerColor = AnimeTheme.colors.background,
                    disabledContentColor = AnimeTheme.colors.surfacePrimaryDisabled
                ),
                onClick = {
                    onReplayVideoClicked()
                }
            ) {
                Icon(
                    painter = painterResource(id = AnimeListIcons.replay),
                    contentDescription = stringResource(com.mumbicodes.designsystem.R.string.back_button),
                    tint = Background_light
                )
            }
        }
    }
}