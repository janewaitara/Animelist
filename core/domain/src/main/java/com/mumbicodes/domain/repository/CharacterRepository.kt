package com.mumbicodes.domain.repository

import com.mumbicodes.common.result.Result
import com.mumbicodes.network.CharacterQuery
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getCharacter(characterId: Int): Flow<Result<CharacterQuery.Character>>
}