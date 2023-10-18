package com.mumbicodes.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mumbicodes.designsystem.R
import com.mumbicodes.designsystem.atoms.Icon
import com.mumbicodes.designsystem.atoms.IconButton
import com.mumbicodes.designsystem.atoms.Text
import com.mumbicodes.designsystem.icons.AnimeListIcons
import com.mumbicodes.designsystem.theme.AnimeListTheme
import com.mumbicodes.designsystem.theme.AnimeTheme

@Composable
fun TextTopBarComponent(
    modifier: Modifier = Modifier,
    headingText: String,
    hasButtonIcon: Boolean = true,
    onBackButtonClicked: () -> Unit = {},
    onListIconButtonClicked: () -> Unit = {},
    onGridIconButtonClicked: () -> Unit = {}
) {
    /**
     * TODO maintain this state even when a user navigates back - do we want to??*/
    var selectedIconButton by rememberSaveable {
        mutableStateOf(SelectedButton.LIST)
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = AnimeTheme.colors.background)
            .padding(
                top = AnimeTheme.space.space16dp,
                bottom = AnimeTheme.space.space8dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AnimeTheme.space.space8dp)
    ) {
        IconButton(
            modifier = Modifier
                .offset((-10).dp),
            onClick = {
                onBackButtonClicked()
            }
        ) {
            Icon(
                painter = painterResource(id = AnimeListIcons.back_arrow),
                contentDescription = stringResource(R.string.back_button),
                tint = AnimeTheme.colors.textStrong
            )
        }

        Text(
            modifier = Modifier
                .weight(1f),
            text = headingText,
            style = AnimeTheme.typography.titleSmall,
            color = AnimeTheme.colors.textStrong
        )

        if (hasButtonIcon) {
            IconButton(
                onClick = {
                    selectedIconButton = SelectedButton.LIST
                    onListIconButtonClicked()
                },
                enabled = selectedIconButton == SelectedButton.LIST
            ) {
                Icon(
                    painter = painterResource(id = AnimeListIcons.list_layout),
                    contentDescription = stringResource(R.string.list_layout)
                )
            }

            IconButton(
                onClick = {
                    selectedIconButton = SelectedButton.GRID
                    onGridIconButtonClicked()
                },
                enabled = selectedIconButton == SelectedButton.GRID
            ) {
                Icon(
                    painter = painterResource(id = AnimeListIcons.grid_layout),
                    contentDescription = stringResource(R.string.grid_layout)
                )
            }
        }
    }
}

enum class SelectedButton {
    LIST, GRID
}

@Preview()
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TestPreview() {
    AnimeListTheme {
        TextTopBarComponent(headingText = stringResource(id = R.string.back_button))
    }
}