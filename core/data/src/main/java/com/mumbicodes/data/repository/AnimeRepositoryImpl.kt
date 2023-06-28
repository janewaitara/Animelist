package com.mumbicodes.data.repository

import com.apollographql.apollo3.ApolloClient
import com.mumbicodes.common.result.Result
import com.mumbicodes.domain.repository.AnimeRepository
import com.mumbicodes.network.AnimeListQuery
import com.mumbicodes.network.AnimeQuery
import com.mumbicodes.network.RecommendationsQuery
import com.mumbicodes.network.type.MediaFormat
import com.mumbicodes.network.type.MediaSort
import com.mumbicodes.network.type.MediaType
import kotlinx.coroutines.flow.Flow

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

    override fun getRecommendations(): Flow<Result<List<RecommendationsQuery.Media>>> {
        TODO("")
    }

    override fun getAnime(animeId: Int, page: Int?, perPage: Int?): Flow<Result<AnimeQuery.Media>> {
        TODO("")
    }
}