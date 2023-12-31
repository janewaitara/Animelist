package com.mumbicodes.data.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.mumbicodes.common.result.Result
import com.mumbicodes.common.result.asResult
import com.mumbicodes.domain.repository.AnimeRepository
import com.mumbicodes.model.data.Anime
import com.mumbicodes.model.data.LocalMediaFormat
import com.mumbicodes.model.data.LocalMediaSort
import com.mumbicodes.model.data.LocalMediaType
import com.mumbicodes.model.data.toModelAnime
import com.mumbicodes.model.data.toNetworkMediaFormat
import com.mumbicodes.model.data.toNetworkMediaType
import com.mumbicodes.network.AnimeListQuery
import com.mumbicodes.network.AnimeQuery
import com.mumbicodes.network.RecommendationsQuery
import kotlinx.coroutines.flow.Flow

class AnimeRepositoryImpl(private val apolloClient: ApolloClient) : AnimeRepository {
    override fun getAnimeList(
        page: Int?,
        perPage: Int?,
        type: LocalMediaType?,
        sortList: List<LocalMediaSort>?,
        formatIn: List<LocalMediaFormat>?
    ): Flow<Result<List<Anime>>> {
        return apolloClient.query(
            AnimeListQuery(
                page = Optional.presentIfNotNull(page),
                perPage = Optional.presentIfNotNull(perPage),
                type = Optional.presentIfNotNull(type?.toNetworkMediaType()),
                sort = Optional.presentIfNotNull(sortList?.map { it.toNetworkMediaType() }),
                formatIn = Optional.presentIfNotNull(
                    formatIn?.map {
                        it.toNetworkMediaFormat()
                    }
                )
            )
        ).fetchPolicy(FetchPolicy.NetworkFirst).toFlow().asResult {
            it.Page?.media?.filterNotNull().orEmpty().map { animeListQueryMedium ->
                animeListQueryMedium.toModelAnime()
            }
        }
    }

    override fun getRecommendations(): Flow<Result<List<Anime>>> {
        return apolloClient.query(
            RecommendationsQuery()
        ).fetchPolicy(FetchPolicy.NetworkFirst)
            .toFlow()
            .asResult {
                it.Page?.recommendations?.filterNotNull().orEmpty().map { recommendationsQuery ->
                    recommendationsQuery.media!!.toModelAnime()
                }.distinct()
            }
    }

    override fun getAnime(animeId: Int, page: Int?, perPage: Int?): Flow<Result<Anime>> {
        return apolloClient.query(
            AnimeQuery(
                mediaId = Optional.present(animeId),
                page = Optional.presentIfNotNull(page),
                perPage = Optional.presentIfNotNull(perPage)
            )
        ).fetchPolicy(FetchPolicy.NetworkFirst)
            .toFlow()
            .asResult {
                it.Media!!.toModelAnime()
            }
    }
}