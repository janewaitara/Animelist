package com.mumbicodes.character

import androidx.lifecycle.ViewModel
import com.mumbicodes.common.result.Result
import com.mumbicodes.domain.repository.CharacterRepository
import com.mumbicodes.network.CharacterQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    fun getCharacterById(characterId: Int): Flow<Result<CharacterQuery.Character>> =
        characterRepository.getCharacter(characterId = characterId)
}