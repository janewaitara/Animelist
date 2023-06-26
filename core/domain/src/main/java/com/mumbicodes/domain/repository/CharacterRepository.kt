package com.mumbicodes.domain.repository

import com.mumbicodes.network.CharacterQuery
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getCharacter(characterId: Int): Flow<CharacterQuery.Character>
}