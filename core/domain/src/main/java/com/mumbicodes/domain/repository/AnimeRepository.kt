package com.mumbicodes.domain.repository

import com.mumbicodes.network.AnimeListQuery
import com.mumbicodes.network.AnimeQuery
import com.mumbicodes.network.RecommendationsQuery
import com.mumbicodes.network.type.MediaFormat
import com.mumbicodes.network.type.MediaSort
import com.mumbicodes.network.type.MediaType
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {

    fun getAnimeList(
        page: Int?,
        perPage: Int?,
        type: MediaType?,
        sortList: List<MediaSort>?,
        formatIn: List<MediaFormat>?
    ): Flow<List<AnimeListQuery.Medium>>

    fun getRecommendations(): Flow<List<RecommendationsQuery.Media>>

    fun getAnime(animeId: Int, page: Int?, perPage: Int?): Flow<AnimeQuery.Media>
}