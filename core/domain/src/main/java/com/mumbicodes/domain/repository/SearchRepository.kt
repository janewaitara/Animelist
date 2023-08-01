package com.mumbicodes.domain.repository

import com.mumbicodes.common.result.Result
import com.mumbicodes.model.data.Anime
import com.mumbicodes.network.SearchCharacterQuery
import com.mumbicodes.network.type.MediaFormat
import com.mumbicodes.network.type.MediaSort
import com.mumbicodes.network.type.MediaType
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun searchAnime(
        searchParam: String,
        type: MediaType?,
        sortList: List<MediaSort>?,
        formatIn: List<MediaFormat>?
    ): Flow<Result<List<Anime>>>

    fun searchCharacter(searchParam: String): Flow<Result<List<SearchCharacterQuery.Character>>>
}