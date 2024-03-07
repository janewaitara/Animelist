package com.mumbicodes.ui.components

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import com.mumbicodes.designsystem.atoms.Icon
import com.mumbicodes.designsystem.atoms.IconButton
import com.mumbicodes.designsystem.atoms.IconButtonColors
import com.mumbicodes.designsystem.icons.AnimeListIcons
import com.mumbicodes.designsystem.theme.AnimeTheme
import com.mumbicodes.designsystem.theme.Background_dark
import com.mumbicodes.designsystem.theme.Background_light
import com.mumbicodes.ui.controller.PlayerControllerState

@OptIn(UnstableApi::class)
@Composable
fun TrailerComponent(
    modifier: Modifier = Modifier,
    playerControllerState: PlayerControllerState,
    trailerPlayer: Player,
    onToggleAudioClicked: () -> Unit,
    onPlayPauseClicked: () -> Unit
) {
    // TODO show video when it's fully loaded
    // TODO When buffering show the play icon as loading

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
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f),

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
                    player = trailerPlayer
                    useController = false
                    setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)/*setShowRewindButton(false)
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
                if (playerControllerState.isVideoPlaying) {
                    AnimeListIcons.pause
                } else {
                    AnimeListIcons.play
                }
            val muteUnMuteIcon =
                if (playerControllerState.isVolumeOn) {
                    AnimeListIcons.mute_audio
                } else {
                    AnimeListIcons.unmute_audio
                }
            IconButton(
                modifier = Modifier,
                colors = IconButtonColors(
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
                colors = IconButtonColors(
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