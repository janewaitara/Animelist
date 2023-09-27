package com.mumbicodes.character

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mumbicodes.character.constants.CHARACTERID
import com.mumbicodes.common.result.Result
import com.mumbicodes.domain.repository.CharacterRepository
import com.mumbicodes.model.data.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // TODO update the id to get from savedState after it's passed from otehr screens
    val characterUiState: StateFlow<CharacterScreenUiState> =
        getCharacterById(
            characterId = savedStateHandle.get<Int>(CHARACTERID) ?: 0
        ).map { characterDetails ->
            when (characterDetails) {
                is Result.ApplicationError -> {
                    CharacterScreenUiState.Error(characterDetails.errors.joinToString())
                }

                is Result.Failure -> {
                    CharacterScreenUiState.Error(characterDetails.exception.message.toString())
                }

                Result.Loading -> {
                    CharacterScreenUiState.Loading
                }

                is Result.Success -> {
                    CharacterScreenUiState.CharacterDetails(data = characterDetails.data)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CharacterScreenUiState.Loading
        )

    private fun getCharacterById(characterId: Int): Flow<Result<Character>> =
        characterRepository.getCharacter(characterId = characterId)
}