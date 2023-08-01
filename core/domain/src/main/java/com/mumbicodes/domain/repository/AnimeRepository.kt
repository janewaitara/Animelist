package com.mumbicodes.domain.repository

import com.mumbicodes.common.result.Result
import com.mumbicodes.model.data.Anime
import com.mumbicodes.model.data.LocalMediaFormat
import com.mumbicodes.model.data.LocalMediaSort
import com.mumbicodes.model.data.LocalMediaType
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {

    fun getAnimeList(
        page: Int?,
        perPage: Int?,
        type: LocalMediaType?,
        sortList: List<LocalMediaSort>?,
        formatIn: List<LocalMediaFormat>?
    ): Flow<Result<List<Anime>>>

    fun getRecommendations(): Flow<Result<List<Anime>>>

    fun getAnime(animeId: Int, page: Int?, perPage: Int?): Flow<Result<Anime>>
}