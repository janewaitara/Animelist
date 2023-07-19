package com.mumbicodes.character

import com.mumbicodes.network.CharacterQuery

sealed interface CharacterScreenUiState {

    object Loading : CharacterScreenUiState

    data class CharacterDetails(
        val data: CharacterQuery.Character
    ) : CharacterScreenUiState

    data class Error(val errorMessage: String) : CharacterScreenUiState
}