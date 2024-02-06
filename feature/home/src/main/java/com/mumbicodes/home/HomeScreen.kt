package com.mumbicodes.home

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import com.mumbicodes.designsystem.atoms.Icon
import com.mumbicodes.designsystem.atoms.IconButton
import com.mumbicodes.designsystem.atoms.IconButtonColors
import com.mumbicodes.designsystem.atoms.Image
import com.mumbicodes.designsystem.components.ErrorBannerComponent
import com.mumbicodes.designsystem.components.HorizontalListLoading
import com.mumbicodes.designsystem.icons.AnimeListIcons
import com.mumbicodes.designsystem.molecules.AnimeSection
import com.mumbicodes.designsystem.theme.AnimeTheme
import com.mumbicodes.designsystem.theme.Background_dark
import com.mumbicodes.designsystem.theme.Background_light
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
    val homeScreenState: HomeScreenState by homeScreenViewModel.homeState.collectAsStateWithLifecycle()

    HomeScreen(
        modifier = modifier,
        homeScreenState = homeScreenState,
        recommendedAnimeUiStates = recommendedAnimeUiStates,
        popularAnimeUiStates = popularAnimeUiStates,
        trendingAnimeUiStates = trendingAnimeUiStates,
        onAnimeClicked = onAnimeClicked,
        onSeeAllButtonClicked = {
            homeScreenViewModel.updateAnimeSortType(it)
            onSeeAllButtonClicked()
        },
        updatePlayerMediaItem = {},
        onToggleAudioClicked = homeScreenViewModel::toggleAudioStateVideo,
        onPlayPauseClicked = homeScreenViewModel::onPlayPauseClicked,
        onReplayVideoClicked = homeScreenViewModel::replayVideo
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeScreenState: HomeScreenState,
    recommendedAnimeUiStates: RecommendedAnimesUiStates,
    popularAnimeUiStates: PopularAnimeStates,
    trendingAnimeUiStates: TrendingAnimeStates,
    onAnimeClicked: (Int) -> Unit,
    onSeeAllButtonClicked: (AnimeSortType) -> Unit,
    updatePlayerMediaItem: (String) -> Unit,
    onToggleAudioClicked: () -> Unit,
    onPlayPauseClicked: () -> Unit,
    onReplayVideoClicked: () -> Unit
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
        /*  var firstTrendingAnime by rememberSaveable{
              mutableStateOf("")
          }
          var firstTrendingAnimeImage by rememberSaveable{
              mutableStateOf("")
          }

          if (firstTrendingAnime.isNotEmpty()) {*/
        TrailerComponent(
            homeScreenState = homeScreenState,
            onToggleAudioClicked = onToggleAudioClicked,
            onPlayPauseClicked = onPlayPauseClicked,
            onReplayVideoClicked = onReplayVideoClicked
        )

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
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f),
            // .height(214.dp),
            coverImageUrl = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/small/bx153518-7FNR7zCxO2X5.jpg"
        )

        // Trending section
        when (trendingAnimeUiStates) {
            is TrendingAnimeStates.TrendingAnimes -> {
                /*  //This is for testing
                  trendingAnimeUiStates.trending.first().let {
                      Log.d("Media trailer 3", it.trailer?.id ?: "No trailer")
                      Log.d("Media trailer 3", it.coverImage ?: "No image")

                      //it.trailer?.id?.let { it1 -> updatePlayerMediaItem(it1) }
                  }
                  firstTrendingAnime = trendingAnimeUiStates.trending.first().trailer?.id ?: ""
                  firstTrendingAnimeImage = trendingAnimeUiStates.trending.first().coverImage ?: ""
  */
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
                ErrorBannerComponent(errorMessage = trendingAnimeUiStates.errorMessage)
            }

            TrendingAnimeStates.Loading -> {
                HorizontalListLoading()
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
                ErrorBannerComponent(errorMessage = recommendedAnimeUiStates.errorMessage)
            }

            RecommendedAnimesUiStates.Loading -> {
                HorizontalListLoading()
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
                ErrorBannerComponent(errorMessage = popularAnimeUiStates.errorMessage)
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

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f),
            // .height(214.dp),
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