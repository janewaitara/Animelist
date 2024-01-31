package com.mumbicodes.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mumbicodes.designsystem.R
import com.mumbicodes.designsystem.atoms.Icon
import com.mumbicodes.designsystem.atoms.Text
import com.mumbicodes.designsystem.icons.AnimeListIcons
import com.mumbicodes.designsystem.theme.AnimeListTheme
import com.mumbicodes.designsystem.theme.AnimeTheme

@Composable
fun SearchFieldComponent(
    modifier: Modifier = Modifier,
    placeholder: String,
    searchParam: String,
    onValueChanged: (String) -> Unit,
    onSearchClicked: () -> Unit = {},
    onClearSearchField: () -> Unit = {}
) {
    var isFocused by rememberSaveable {
        mutableStateOf(false)
    }
    // TODO add the different states
    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
        value = searchParam,
        onValueChange = onValueChanged,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearchClicked() }),
        cursorBrush = SolidColor(AnimeTheme.colors.textNeutral),
        maxLines = 1,
        textStyle = AnimeTheme.typography.bodyMedium.copy(color = AnimeTheme.colors.textNeutral),
        decorationBox = { innerTextField ->

            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .clip(AnimeTheme.shapes.smallShape)
                    .background(
                        color = AnimeTheme.colors.searchBarBackground
                    )
                    .padding(AnimeTheme.space.space12dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AnimeTheme.space.space12dp)
            ) {
                Icon(
                    modifier = Modifier.size(AnimeTheme.space.space16dp),
                    painter = painterResource(AnimeListIcons.searchOutlined),
                    contentDescription = stringResource(R.string.search),
                    tint = AnimeTheme.colors.textWeak
                )

                if (!isFocused && searchParam.isEmpty()) {
                    Text(
                        modifier = Modifier,
                        text = stringResource(R.string.search_placeholder, placeholder),
                        style = AnimeTheme.typography.bodyMedium,
                        color = AnimeTheme.colors.searchBarPlaceHolderText,
                        textAlign = TextAlign.Start
                    )
                }
                if (isFocused || searchParam.isNotEmpty()) {
                    Box(modifier = Modifier.weight(1f)) {
                        innerTextField()
                    }
                }

                if (searchParam.isNotEmpty()) {
                    Icon(
                        modifier = Modifier.size(AnimeTheme.space.space24dp)
                            .clickable {
                                onClearSearchField()
                            },
                        painter = painterResource(AnimeListIcons.close),
                        contentDescription = stringResource(R.string.clear),
                        tint = AnimeTheme.colors.textWeak
                    )
                }
                // TODO add a trailing icon to clear search param
            }
        }
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchFieldComponentPreview() {
    AnimeListTheme {
        var search by remember { mutableStateOf("") }
        Column(
            Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            SearchFieldComponent(
                placeholder = "an anime",
                searchParam = search,
                onValueChanged = {
                    search = it
                }
            )
        }
    }
}