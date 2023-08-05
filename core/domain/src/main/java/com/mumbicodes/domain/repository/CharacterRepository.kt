package com.mumbicodes.domain.repository

import com.mumbicodes.common.result.Result
import com.mumbicodes.model.data.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getCharacter(characterId: Int): Flow<Result<Character>>
}