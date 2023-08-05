package com.mumbicodes.data.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.mumbicodes.common.result.Result
import com.mumbicodes.common.result.asResult
import com.mumbicodes.domain.repository.CharacterRepository
import com.mumbicodes.model.data.Character
import com.mumbicodes.model.data.toModelCharacter
import com.mumbicodes.network.CharacterQuery
import kotlinx.coroutines.flow.Flow

class CharacterRepositoryImpl(private val apolloClient: ApolloClient) : CharacterRepository {
    override fun getCharacter(characterId: Int): Flow<Result<Character>> {
        return apolloClient.query(
            CharacterQuery(characterId = Optional.present(characterId))
        ).fetchPolicy(FetchPolicy.NetworkFirst).toFlow().asResult {
            it.Character!!.toModelCharacter()
        }
    }
}