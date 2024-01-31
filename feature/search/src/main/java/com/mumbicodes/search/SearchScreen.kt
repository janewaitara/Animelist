package com.mumbicodes.search

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
        onCharacterClicked = onCharacterClicked,
        onClearSearchField = searchViewModel::clearSearchParam
    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun SearchScreenContent(
    modifier: Modifier = Modifier,
    searchScreenState: SearchScreenState,
    onSearchValueChanged: (String) -> Unit,
    onFilterChipClicked: (SearchType) -> Unit,
    onSearchClicked: () -> Unit = {},
    onAnimeClicked: (Int) -> Unit = {},
    onCharacterClicked: (Int) -> Unit = {},
    onClearSearchField: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = AnimeTheme.colors.background)
        // .padding(bottom = 72.dp)
    ) {
        // the contentPadding: 112 + 4 + 60
        when (searchScreenState.searchMainFilter) {
            SearchType.ANIME -> {
                when (searchScreenState.animeSearchResultsState) {
                    is AnimeSearchUiState.AnimeResults -> {
                        LazyColumn(
                            modifier = Modifier
                                .background(color = AnimeTheme.colors.background)
                                .padding(horizontal = AnimeTheme.space.space20dp),
                            contentPadding = PaddingValues(bottom = 176.dp),
                            verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space16dp)
                        ) {
                            stickyHeader {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(color = AnimeTheme.colors.background)
                                        .padding(
                                            vertical = AnimeTheme.space.space8dp
                                        ),
                                    text = stringResource(id = R.string.search_results),
                                    color = AnimeTheme.colors.textNeutral,
                                    style = AnimeTheme.typography.titleSmall,
                                    textAlign = TextAlign.Start
                                )
                            }
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
                        EmptyListSection(searchParam = searchScreenState.searchParam)

                    is AnimeSearchUiState.Error ->
                        ErrorBannerComponent(errorMessage = searchScreenState.animeSearchResultsState.errorMessage)

                    AnimeSearchUiState.Loading -> ListLoadingComponent()
                    AnimeSearchUiState.EmptyState -> EmptyStateSection()
                }
            }

            SearchType.CHARACTER -> {
                when (searchScreenState.characterSearchResultsState) {
                    is CharacterSearchUiState.CharacterResults -> {
                        LazyColumn(
                            modifier = Modifier
                                .background(color = AnimeTheme.colors.background)
                                .padding(horizontal = AnimeTheme.space.space20dp),
                            contentPadding = PaddingValues(bottom = 176.dp),
                            verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space16dp)
                        ) {
                            stickyHeader {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(color = AnimeTheme.colors.background)
                                        .padding(
                                            vertical = AnimeTheme.space.space8dp
                                        ),
                                    text = stringResource(id = R.string.search_results),
                                    color = AnimeTheme.colors.textNeutral,
                                    style = AnimeTheme.typography.titleSmall,
                                    textAlign = TextAlign.Start
                                )
                            }
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
                        EmptyListSection(searchParam = searchScreenState.searchParam)

                    is CharacterSearchUiState.Error ->
                        ErrorBannerComponent(errorMessage = searchScreenState.characterSearchResultsState.errorMessage)

                    CharacterSearchUiState.Loading -> ListLoadingComponent()
                    CharacterSearchUiState.EmptyState -> EmptyStateSection()
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            SearchAndFilterSection(
                modifier = Modifier,
                searchParam = searchScreenState.searchParam,
                searchFilter = searchScreenState.searchMainFilter,
                onSearchValueChanged = onSearchValueChanged,
                onFilterChipClicked = onFilterChipClicked,
                onSearchClicked = onSearchClicked,
                onClearSearchField = onClearSearchField
            )
            AnimatedVisibility(visible = WindowInsets.isImeVisible) {
                Spacer(Modifier.windowInsetsBottomHeight(WindowInsets(bottom = 60.dp)))
            }

            // This is the space after all content has been scrolled that accounts for the bottom Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(67.dp)
                    .background(color = Color.Transparent)
            )
        }
    }
}

@Composable
fun EmptyStateSection(
    modifier: Modifier = Modifier
) {
    val searchIllustration = when (isSystemInDarkTheme()) {
        true -> R.drawable.search_dark_mode
        false -> R.drawable.search_light_mode
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = AnimeTheme.space.space20dp,
                end = AnimeTheme.space.space20dp,
                top = 96.dp
            )
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.search_empty_state),
            color = AnimeTheme.colors.textNeutral,
            style = AnimeTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        Image(
            modifier = Modifier.align(Alignment.Center),
            painter = painterResource(id = searchIllustration),
            contentDescription = "Search illustration"
        )
    }
}

@Composable
fun EmptyListSection(
    modifier: Modifier = Modifier,
    searchParam: String
) {
    val noResultIllustration = when (isSystemInDarkTheme()) {
        true -> R.drawable.no_search_results_dark
        false -> R.drawable.no_search_results_light
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = AnimeTheme.space.space20dp,
                end = AnimeTheme.space.space20dp,
                top = 96.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space48dp)
    ) {
        Image(
            modifier = Modifier,
            painter = painterResource(id = noResultIllustration),
            contentDescription = "No results found illustration"
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.no_search_results, searchParam),
            color = AnimeTheme.colors.textNeutral,
            style = AnimeTheme.typography.bodyLargeBold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SearchAndFilterSection(
    modifier: Modifier = Modifier,
    searchParam: String,
    searchFilter: SearchType,
    onSearchValueChanged: (String) -> Unit,
    onFilterChipClicked: (SearchType) -> Unit,
    onSearchClicked: () -> Unit,
    onClearSearchField: () -> Unit
) {
    Column(
        modifier = modifier
            .background(color = AnimeTheme.colors.searchSectionBorderColor)
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
            modifier = Modifier,
            placeholder = when (searchFilter) {
                SearchType.ANIME -> "an anime"
                SearchType.CHARACTER -> "a character"
            },
            searchParam = searchParam,
            onValueChanged = onSearchValueChanged,
            onSearchClicked = onSearchClicked,
            onClearSearchField = onClearSearchField
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