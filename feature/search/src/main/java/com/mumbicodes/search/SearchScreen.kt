package com.mumbicodes.search

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mumbicodes.common.result.toCamelCase
import com.mumbicodes.designsystem.atoms.FilterChip
import com.mumbicodes.designsystem.components.SearchFieldComponent
import com.mumbicodes.designsystem.theme.AnimeListTheme
import com.mumbicodes.designsystem.theme.AnimeTheme

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    SearchScreenContent(
        modifier = modifier,
        searchParam = "",
        searchFilter = SearchType.ANIME,
        onFilterChipClicked = {},
        onSearchValueChanged = {}
    )
}

@Composable
fun SearchScreenContent(
    modifier: Modifier = Modifier,
    searchParam: String,
    searchFilter: SearchType,
    onSearchValueChanged: (String) -> Unit,
    onFilterChipClicked: (SearchType) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = AnimeTheme.colors.background)
            .padding(bottom = 72.dp)
    ) {
        SearchAndFilterSection(
            modifier = Modifier.align(Alignment.BottomCenter),
            searchParam = searchParam,
            searchFilter = searchFilter,
            onSearchValueChanged = onSearchValueChanged,
            onFilterChipClicked = onFilterChipClicked
        )
    }
}

@Composable
fun SearchAndFilterSection(
    modifier: Modifier = Modifier,
    searchParam: String,
    searchFilter: SearchType,
    onSearchValueChanged: (String) -> Unit,
    onFilterChipClicked: (SearchType) -> Unit
) {
    Column(
        modifier = modifier
            .background(
                color = AnimeTheme.colors.searchSectionBorderColor
            )
            .padding(vertical = 1.dp)
            .background(color = AnimeTheme.colors.bottomNavBackground)
            .fillMaxWidth()
            .padding(AnimeTheme.space.space12dp),
        verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space12dp)
    ) {
        /**
         * Todo research why the text lineheight isn't the overall height of the atom.
         * */
        SearchFieldComponent(
            placeholder = when (searchFilter) {
                SearchType.ANIME -> "an anime"
                SearchType.CHARACTER -> "a character"
            },
            searchParam = searchParam,
            onValueChanged = onSearchValueChanged
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(AnimeTheme.space.space8dp)
        ) {
            FilterChip(
                text = stringResource(id = R.string.anime),
                selected = searchFilter.name.lowercase().toCamelCase() == stringResource(id = R.string.anime),
                onClick = { onFilterChipClicked(SearchType.ANIME) }
            )

            FilterChip(
                text = stringResource(id = R.string.character),
                selected = searchFilter.name.lowercase().toCamelCase() == stringResource(id = R.string.character),
                onClick = { onFilterChipClicked(SearchType.CHARACTER) }
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchScreenPreview() {
    AnimeListTheme {
        SearchScreenContent(
            searchParam = "",
            searchFilter = SearchType.ANIME,
            onFilterChipClicked = {},
            onSearchValueChanged = {}
        )
    }
}