package com.mumbicodes.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mumbicodes.designsystem.R
import com.mumbicodes.designsystem.atoms.Icon
import com.mumbicodes.designsystem.atoms.Text
import com.mumbicodes.designsystem.icons.AnimeListIcons
import com.mumbicodes.designsystem.theme.AnimeListTheme
import com.mumbicodes.designsystem.theme.AnimeTheme

@Composable
fun ErrorBannerComponent(
    modifier: Modifier = Modifier,
    errorMessage: String
) {
    Row(
        modifier = modifier
            .clip(shape = AnimeTheme.shapes.smallShape)
            .background(color = AnimeTheme.colors.onError)
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(AnimeTheme.space.space8dp)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .width(3.dp)
                .background(color = AnimeTheme.colors.darkErrorText)
        )

        Row(
            Modifier.padding(
                top = AnimeTheme.space.space12dp,
                bottom = AnimeTheme.space.space12dp,
                end = AnimeTheme.space.space16dp
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AnimeTheme.space.space8dp)
        ) {
            Icon(
                painter = painterResource(AnimeListIcons.warning),
                contentDescription = stringResource(R.string.error_icon),
                tint = AnimeTheme.colors.darkErrorText
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = errorMessage,
                color = AnimeTheme.colors.textNeutral,
                style = AnimeTheme.typography.bodyMedium,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, device = Devices.PIXEL)
@Composable
fun ErrorBannerComponentPreview() {
    AnimeListTheme {
        ErrorBannerComponent(errorMessage = "This is an error messae wwdewdewdedewd")
    }
}