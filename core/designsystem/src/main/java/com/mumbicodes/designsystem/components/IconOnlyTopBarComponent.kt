package com.mumbicodes.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mumbicodes.designsystem.R
import com.mumbicodes.designsystem.atoms.Icon
import com.mumbicodes.designsystem.atoms.IconButton
import com.mumbicodes.designsystem.atoms.IconButtonColors
import com.mumbicodes.designsystem.icons.AnimeListIcons
import com.mumbicodes.designsystem.theme.AnimeListTheme
import com.mumbicodes.designsystem.theme.AnimeTheme
import com.mumbicodes.designsystem.theme.Background_dark
import com.mumbicodes.designsystem.theme.Background_light

@Composable
fun IconTopBarComponent(
    modifier: Modifier = Modifier,
    onBackButtonClicked: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = AnimeTheme.space.space16dp,
                bottom = AnimeTheme.space.space8dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AnimeTheme.space.space8dp)
    ) {
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
                onBackButtonClicked()
            }
        ) {
            Icon(
                painter = painterResource(id = AnimeListIcons.back_arrow),
                contentDescription = stringResource(R.string.back_button),
                tint = Background_light
            )
        }
    }
}

@Preview()
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun IconTopBarPreview() {
    AnimeListTheme {
        IconTopBarComponent()
    }
}