package com.mumbicodes.domain.repository

import com.mumbicodes.common.result.Result
import com.mumbicodes.model.data.Anime
import com.mumbicodes.model.data.Character
import com.mumbicodes.model.data.LocalMediaFormat
import com.mumbicodes.model.data.LocalMediaSort
import com.mumbicodes.model.data.LocalMediaType
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun searchAnime(
        searchParam: String,
        type: LocalMediaType?,
        sortList: List<LocalMediaSort>?,
        formatIn: List<LocalMediaFormat>?
    ): Flow<Result<List<Anime>>>

    fun searchCharacter(searchParam: String): Flow<Result<List<Character>>>
}