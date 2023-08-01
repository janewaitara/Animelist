package com.mumbicodes.data.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.mumbicodes.common.result.Result
import com.mumbicodes.common.result.asResult
import com.mumbicodes.domain.repository.SearchRepository
import com.mumbicodes.model.data.Anime
import com.mumbicodes.model.data.Character
import com.mumbicodes.model.data.LocalMediaFormat
import com.mumbicodes.model.data.LocalMediaSort
import com.mumbicodes.model.data.LocalMediaType
import com.mumbicodes.model.data.toModelAnime
import com.mumbicodes.model.data.toModelCharacter
import com.mumbicodes.model.data.toNetworkMediaFormat
import com.mumbicodes.model.data.toNetworkMediaType
import com.mumbicodes.network.SearchAnimeQuery
import com.mumbicodes.network.SearchCharacterQuery
import kotlinx.coroutines.flow.Flow

class SearchRepositoryImpl(private val apolloClient: ApolloClient) : SearchRepository {
    override fun searchAnime(
        searchParam: String,
        type: LocalMediaType?,
        sortList: List<LocalMediaSort>?,
        formatIn: List<LocalMediaFormat>?
    ): Flow<Result<List<Anime>>> {
        return apolloClient.query(
            SearchAnimeQuery(
                search = Optional.present(searchParam),
                type = Optional.presentIfNotNull(type?.toNetworkMediaType()),
                sort = Optional.presentIfNotNull(sortList?.map { it.toNetworkMediaType() }),
                formatIn = Optional.presentIfNotNull((formatIn?.map { it.toNetworkMediaFormat() }))
            )
        ).fetchPolicy(FetchPolicy.NetworkFirst)
            .toFlow()
            .asResult {
                it.Page?.media?.filterNotNull().orEmpty().map { searchAnimeQueryMedium ->
                    searchAnimeQueryMedium.toModelAnime()
                }
            }
    }

    override fun searchCharacter(searchParam: String): Flow<Result<List<Character>>> {
        return apolloClient.query(
            SearchCharacterQuery(search = Optional.present(searchParam))
        ).fetchPolicy(FetchPolicy.NetworkFirst)
            .toFlow()
            .asResult {
                it.Page?.characters?.filterNotNull().orEmpty().map { searchCharacterQueryCharacter ->
                    searchCharacterQueryCharacter.toModelCharacter()
                }
            }
    }
}