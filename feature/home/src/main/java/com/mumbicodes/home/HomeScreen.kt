package com.mumbicodes.home

import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
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
        onAnimeClicked = onAnimeClicked,
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
        // These states are for testing
        // TODO which image is this coming from:
        // TODO update the media item when a an anime comes in view in the ViewPager

        /*TrailerComponent(
            homeScreenState = homeScreenState,
            onToggleAudioClicked = onToggleAudioClicked,
            onPlayPauseClicked = onPlayPauseClicked,
            onReplayVideoClicked = onReplayVideoClicked
        )*/

        /*  AndroidView(
              modifier = Modifier
                  .fillMaxWidth()
                  .aspectRatio(16 / 9f)
                  .height(214.dp),
              factory = { context ->
                  PlayerView(context).apply {
                      player = homeScreenState.player
                      useController = false
                  }
              })*/
        // }
        /*Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f),
            // .height(214.dp),
            coverImageUrl = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/small/bx153518-7FNR7zCxO2X5.jpg"
        )
        */
        // Trending section
        when (homeScreenState.trendingAnimesUiState) {
            is TrendingAnimeStates.TrendingAnimes -> {
                TrendingViewPager(
                    trending = homeScreenState.trendingAnimes.take(3),
                    onTrendingAnimeSwiped = onTrendingAnimeSwiped,
                    homeScreenState = homeScreenState,
                    onToggleAudioClicked = onToggleAudioClicked,
                    onPlayPauseClicked = onPlayPauseClicked,
                    onReplayVideoClicked = onReplayVideoClicked
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
                        items(homeScreenState.trendingAnimesUiState.trending) { anime ->
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
                ErrorBannerComponent(errorMessage = homeScreenState.trendingAnimesUiState.errorMessage)
            }

            TrendingAnimeStates.Loading -> {
                HorizontalListLoading()
            }
        }

        // Recommended section
        when (homeScreenState.recommendedAnimesUiStates) {
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
                        items(homeScreenState.recommendedAnimesUiStates.recommended) { anime ->
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
                ErrorBannerComponent(errorMessage = homeScreenState.recommendedAnimesUiStates.errorMessage)
            }

            RecommendedAnimesUiStates.Loading -> {
                HorizontalListLoading()
            }
        }

        // Popular section
        when (homeScreenState.popularAnimesUiState) {
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
                        items(homeScreenState.popularAnimesUiState.popular) { anime ->
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
                ErrorBannerComponent(errorMessage = homeScreenState.popularAnimesUiState.errorMessage)
            }

            PopularAnimeStates.Loading -> {
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
    onReplayVideoClicked: () -> Unit
) {
    val scope = rememberCoroutineScope()

    var showVideo by remember {
        mutableStateOf(false)
    }
    var videoHasEnded by remember {
        mutableStateOf(false)
    }
    videoHasEnded = when (homeScreenState.playerState) {
        PlayerState.ENDEND -> {
            true
        }

        else -> {
            false
        }
    }

    LaunchedEffect(Unit) {
        scope.launch {
            showVideo = false
            delay(5000)
            showVideo = true
        }
    }

    Box(
        modifier = modifier
            .height(300.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        trending.reversed().forEachIndexed { index, anime ->
            key(anime) {
                val animatedScaleX by animateFloatAsState(
                    targetValue = 1f - (trending.size - index) * 0.15f,
                    label = ""
                )
                val animatedScaleY by animateFloatAsState(
                    targetValue = 1f - (trending.size - index) * 0.05f,
                    label = ""
                )
                val animatedYOffset by animateDpAsState(
                    targetValue = ((trending.size - index) * -32).dp,
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
                        .swipeToDismiss {
                            onTrendingAnimeSwiped(anime.id)
                        }
                        .border(
                            width = 5.dp,
                            shape = AnimeTheme.shapes.mediumShape,
                            color = AnimeTheme.colors.primary.copy(0.3f)
                        )
                        .clip(shape = AnimeTheme.shapes.mediumShape)
                ) {
                    if (trending[index] == trending.last()) {
                        AnimatedVisibility(visible = showVideo.not() || videoHasEnded) {
                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(16 / 9f),
                                contentScale = ContentScale.FillBounds,
                                // .height(214.dp),
                                coverImageUrl = anime.coverImage
                            )
                        }
                        AnimatedVisibility(visible = showVideo && videoHasEnded.not()) {
                            TrailerComponent(
                                homeScreenState = homeScreenState,
                                onToggleAudioClicked = onToggleAudioClicked,
                                onPlayPauseClicked = onPlayPauseClicked,
                                onReplayVideoClicked = onReplayVideoClicked
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

@OptIn(UnstableApi::class)
@Composable
fun TrailerComponent(
    modifier: Modifier = Modifier,
    homeScreenState: HomeScreenState,
    // trailerPlayer: Player,
    onToggleAudioClicked: () -> Unit,
    onPlayPauseClicked: () -> Unit,
    onReplayVideoClicked: () -> Unit
) {
    // TODO update the icon if to replay player is ended
    // TODO show video when it's fully loaded
    // When buffering show the play icon as loading

    /**
     * This is needed to pause the video when the activity is in the background
     * */
    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            lifecycle = event
        }

        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .aspectRatio(16 / 9f)
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f),
            // .height(214.dp),
            update = { playerView ->
                when (lifecycle) {
                    Lifecycle.Event.ON_RESUME -> {
                        playerView.onResume()
                    }

                    Lifecycle.Event.ON_PAUSE -> {
                        playerView.onPause()
                        playerView.player?.pause()
                    }

                    else -> {}
                }
            },
            factory = { context ->
                PlayerView(context).apply {
                    player = homeScreenState.player
                    useController = false
                    setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
                    /*setShowRewindButton(false)
                    setShowFastForwardButton(false)
                    setShowNextButton(false)
                    setShowPreviousButton(false)
                    setShowPlayButtonIfPlaybackIsSuppressed(false)
                    setShowMultiWindowTimeBar(false)*/
                }
            }
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(AnimeTheme.space.space12dp),
            verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space8dp)
        ) {
            val playPauseIcon =
                if (homeScreenState.isVideoPlaying) AnimeListIcons.pause else AnimeListIcons.play
            val muteUnMuteIcon =
                if (homeScreenState.isVolumeOn) AnimeListIcons.mute_audio else AnimeListIcons.unmute_audio
            IconButton(
                modifier = Modifier,
                colors =
                IconButtonColors(
                    containerColor = Background_dark.copy(alpha = 0.75f),
                    contentColor = Background_light,
                    disabledContainerColor = AnimeTheme.colors.background,
                    disabledContentColor = AnimeTheme.colors.surfacePrimaryDisabled
                ),
                onClick = {
                    onToggleAudioClicked()
                }
            ) {
                Icon(
                    painter = painterResource(id = muteUnMuteIcon),
                    contentDescription = stringResource(com.mumbicodes.designsystem.R.string.back_button),
                    tint = Background_light
                )
            }

            IconButton(
                modifier = Modifier,
                colors =
                IconButtonColors(
                    containerColor = Background_dark.copy(alpha = 0.75f),
                    contentColor = Background_light,
                    disabledContainerColor = AnimeTheme.colors.background,
                    disabledContentColor = AnimeTheme.colors.surfacePrimaryDisabled
                ),
                onClick = {
                    onPlayPauseClicked()
                }
            ) {
                Icon(
                    painter = painterResource(id = playPauseIcon),
                    contentDescription = stringResource(com.mumbicodes.designsystem.R.string.back_button),
                    tint = Background_light
                )
            }
        }
    }
}