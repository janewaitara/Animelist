package com.mumbicodes.search

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mumbicodes.common.result.toCamelCase
import com.mumbicodes.designsystem.atoms.FilterChip
import com.mumbicodes.designsystem.atoms.Text
import com.mumbicodes.designsystem.components.ErrorBannerComponent
import com.mumbicodes.designsystem.components.ListLoadingComponent
import com.mumbicodes.designsystem.components.SearchAnimeComponent
import com.mumbicodes.designsystem.components.SearchCharacterComponent
import com.mumbicodes.designsystem.components.SearchFieldComponent
import com.mumbicodes.designsystem.theme.AnimeListTheme
import com.mumbicodes.designsystem.theme.AnimeTheme

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel(),
    onAnimeClicked: (Int) -> Unit = {},
    onCharacterClicked: (Int) -> Unit = {}
) {
    val searchScreenState by searchViewModel.searchScreenState.collectAsStateWithLifecycle()

    SearchScreenContent(
        modifier = modifier,
        searchScreenState = searchScreenState,
        onFilterChipClicked = searchViewModel::updateSearchFilter,
        onSearchValueChanged = searchViewModel::onSearchParameterChanged,
        onSearchClicked = searchViewModel::onSearchClicked,
        onAnimeClicked = onAnimeClicked,
        onCharacterClicked = onCharacterClicked
    )
}

@Composable
fun SearchScreenContent(
    modifier: Modifier = Modifier,
    searchScreenState: SearchScreenState,
    onSearchValueChanged: (String) -> Unit,
    onFilterChipClicked: (SearchType) -> Unit,
    onSearchClicked: () -> Unit = {},
    onAnimeClicked: (Int) -> Unit = {},
    onCharacterClicked: (Int) -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = AnimeTheme.colors.background)
            .padding(bottom = 72.dp)
    ) {
        when (searchScreenState.searchMainFilter) {
            SearchType.ANIME -> {
                when (searchScreenState.animeSearchResultsState) {
                    is AnimeSearchUiState.AnimeResults -> {
                        LazyColumn(
                            modifier = Modifier
                                .background(color = AnimeTheme.colors.background)
                                .padding(horizontal = AnimeTheme.space.space20dp),
                            contentPadding = PaddingValues(bottom = AnimeTheme.space.space20dp),
                            verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space16dp)
                        ) {
                            items(searchScreenState.animeSearchResultsState.data) { anime ->
                                SearchAnimeComponent(
                                    coverImageUrl = anime.coverImage,
                                    animeEnglishTitle = anime.title?.english ?: "",
                                    animeNativeTitle = anime.title?.native ?: "",
                                    numberOfEpisodes = anime.episodes ?: 0,
                                    episodeDuration = anime.duration ?: 0,
                                    onClick = { onAnimeClicked(anime.id) }
                                )
                            }
                        }
                    }

                    AnimeSearchUiState.EmptyList ->
                        EmptyStateSection(searchFilter = "Search Anime")

                    is AnimeSearchUiState.Error ->
                        ErrorBannerComponent(errorMessage = searchScreenState.animeSearchResultsState.errorMessage)

                    AnimeSearchUiState.Loading -> ListLoadingComponent()
                }
            }

            SearchType.CHARACTER -> {
                when (searchScreenState.characterSearchResultsState) {
                    is CharacterSearchUiState.CharacterResults -> {
                        LazyColumn(
                            modifier = Modifier
                                .background(color = AnimeTheme.colors.background)
                                .padding(horizontal = AnimeTheme.space.space20dp),
                            contentPadding = PaddingValues(bottom = AnimeTheme.space.space20dp),
                            verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space16dp)
                        ) {
                            items(searchScreenState.characterSearchResultsState.data) { character ->
                                SearchCharacterComponent(
                                    characterEnglishName = character.name?.full ?: "",
                                    characterNativeName = character.name?.native ?: "",
                                    characterImageUrl = character.image,
                                    age = character.age,
                                    gender = character.gender,
                                    onClick = { onCharacterClicked(character.id) }
                                )
                            }
                        }
                    }

                    CharacterSearchUiState.EmptyList ->
                        EmptyStateSection(searchFilter = "Search Character")

                    is CharacterSearchUiState.Error ->
                        ErrorBannerComponent(errorMessage = searchScreenState.characterSearchResultsState.errorMessage)

                    CharacterSearchUiState.Loading -> ListLoadingComponent()
                }
            }
        }
        SearchAndFilterSection(
            modifier = Modifier.align(Alignment.BottomCenter),
            searchParam = searchScreenState.searchParam,
            searchFilter = searchScreenState.searchMainFilter,
            onSearchValueChanged = onSearchValueChanged,
            onFilterChipClicked = onFilterChipClicked,
            onSearchClicked = onSearchClicked
        )
    }
}

@Composable
fun EmptyStateSection(
    modifier: Modifier = Modifier,
    searchFilter: String
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = searchFilter,
        color = AnimeTheme.colors.textNeutral,
        style = AnimeTheme.typography.bodyMediumBold,
        textAlign = TextAlign.Start
    )
}

@Composable
fun SearchAndFilterSection(
    modifier: Modifier = Modifier,
    searchParam: String,
    searchFilter: SearchType,
    onSearchValueChanged: (String) -> Unit,
    onFilterChipClicked: (SearchType) -> Unit,
    onSearchClicked: () -> Unit
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
            onValueChanged = onSearchValueChanged,
            onSearchClicked = onSearchClicked
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(AnimeTheme.space.space8dp)
        ) {
            FilterChip(
                text = stringResource(id = R.string.anime),
                selected = searchFilter.name.lowercase()
                    .toCamelCase() == stringResource(id = R.string.anime),
                onClick = { onFilterChipClicked(SearchType.ANIME) }
            )

            FilterChip(
                text = stringResource(id = R.string.character),
                selected = searchFilter.name.lowercase()
                    .toCamelCase() == stringResource(id = R.string.character),
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
            searchScreenState = SearchScreenState(),
            onFilterChipClicked = {},
            onSearchValueChanged = {}
        )
    }
}