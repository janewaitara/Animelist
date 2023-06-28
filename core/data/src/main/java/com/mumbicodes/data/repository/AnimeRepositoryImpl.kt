package com.mumbicodes.data.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.mumbicodes.common.result.Result
import com.mumbicodes.common.result.asResult
import com.mumbicodes.domain.repository.AnimeRepository
import com.mumbicodes.network.AnimeListQuery
import com.mumbicodes.network.AnimeQuery
import com.mumbicodes.network.RecommendationsQuery
import com.mumbicodes.network.type.MediaFormat
import com.mumbicodes.network.type.MediaSort
import com.mumbicodes.network.type.MediaType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalCoroutinesApi::class)
class AnimeRepositoryImpl(private val apolloClient: ApolloClient) : AnimeRepository {
    override fun getAnimeList(
        page: Int?,
        perPage: Int?,
        type: MediaType?,
        sortList: List<MediaSort>?,
        formatIn: List<MediaFormat>?
    ): Flow<Result<List<AnimeListQuery.Medium>>> {
        TODO("")
    }

    override fun getRecommendations(): Flow<Result<List<RecommendationsQuery.Recommendation>>> {
        return apolloClient.query(
            RecommendationsQuery()
        ).fetchPolicy(FetchPolicy.NetworkFirst)
            .toFlow()
            .asResult {
                it.Page?.recommendations?.filterNotNull().orEmpty()
            }
    }

    override fun getAnime(animeId: Int, page: Int?, perPage: Int?): Flow<Result<AnimeQuery.Media>> {
        return apolloClient.query(
            AnimeQuery(
                mediaId = Optional.present(animeId),
                page = Optional.presentIfNotNull(page),
                perPage = Optional.presentIfNotNull(perPage)
            )
        ).fetchPolicy(FetchPolicy.NetworkFirst)
            .toFlow()
            .asResult {
                it.Media!!
            }
    }
}