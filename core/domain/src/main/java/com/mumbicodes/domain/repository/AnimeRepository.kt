package com.mumbicodes.domain.repository

import com.mumbicodes.common.result.Result
import com.mumbicodes.model.data.Anime
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
    ): Flow<Result<List<Anime>>>

    fun getRecommendations(): Flow<Result<List<Anime>>>

    fun getAnime(animeId: Int, page: Int?, perPage: Int?): Flow<Result<Anime>>
}