package com.mumbicodes.domain.repository

import com.mumbicodes.network.AnimeListQuery
import com.mumbicodes.network.AnimeQuery
import com.mumbicodes.network.RecommendationsQuery
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {

    fun getAnimeList(): Flow<List<AnimeListQuery.Medium>>

    fun getRecommendations(): Flow<List<RecommendationsQuery.Media>>

    fun getAnime(animeId: Int, page: Int, perPage: Int): Flow<AnimeQuery.Media>
}