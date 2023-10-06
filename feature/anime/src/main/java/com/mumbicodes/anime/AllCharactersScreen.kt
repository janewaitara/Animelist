package com.mumbicodes.anime

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mumbicodes.designsystem.components.HorizontalCharacterComponent
import com.mumbicodes.designsystem.components.ListLoadingComponent
import com.mumbicodes.designsystem.components.TextTopBarComponent
import com.mumbicodes.designsystem.theme.AnimeTheme

@Composable
fun AllCharactersRoute(
    modifier: Modifier = Modifier,
    animeDetailsViewModel: AnimeViewModel = hiltViewModel(),
    onCharacterClicked: (Int) -> Unit = {},
    onBackButtonClicked: () -> Unit = {}
) {
    val animeDetailsUiState by animeDetailsViewModel.animeDetails.collectAsStateWithLifecycle()

    AllCharactersScreen(
        modifier = modifier,
        animeDetailsUiState = animeDetailsUiState,
        onCharacterClicked = onCharacterClicked,
        onBackButtonClicked = onBackButtonClicked
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AllCharactersScreen(
    modifier: Modifier = Modifier,
    animeDetailsUiState: AnimeDetailsScreenUiState,
    onCharacterClicked: (Int) -> Unit = {},
    onBackButtonClicked: () -> Unit = {}
) {
    when (animeDetailsUiState) {
        is AnimeDetailsScreenUiState.AnimeDetails -> {
            LazyColumn(
                modifier = Modifier
                    .background(color = AnimeTheme.colors.background)
                    .padding(horizontal = AnimeTheme.space.space20dp),
                contentPadding = PaddingValues(bottom = AnimeTheme.space.space20dp),
                verticalArrangement = Arrangement.spacedBy(AnimeTheme.space.space16dp)
            ) {
                stickyHeader {
                    TextTopBarComponent(
                        onBackButtonClicked = {
                            onBackButtonClicked()
                        },
                        hasButtonIcon = false,
                        headingText = stringResource(id = R.string.allCharacters)
                    )
                }
                items(animeDetailsUiState.animeDetails.characters ?: listOf()) { character ->
                    character?.let {
                        HorizontalCharacterComponent(
                            modifier = Modifier,
                            characterImageUrl = character.image,
                            characterEnglishName = character.name?.full ?: "",
                            characterNativeName = character.name?.native ?: "",
                            age = character.age,
                            gender = character.gender,
                            onClick = { onCharacterClicked(character.id) }
                        )
                    }
                }
            }
        }
        is AnimeDetailsScreenUiState.Error -> TODO()
        is AnimeDetailsScreenUiState.Loading -> {
            ListLoadingComponent()
        }
    }
}