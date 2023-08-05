package com.mumbicodes.character

import com.mumbicodes.model.data.Character

sealed interface CharacterScreenUiState {

    object Loading : CharacterScreenUiState

    data class CharacterDetails(
        val data: Character
    ) : CharacterScreenUiState

    data class Error(val errorMessage: String) : CharacterScreenUiState
}