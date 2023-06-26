package com.mumbicodes.domain.repository

import com.mumbicodes.network.SearchAnimeQuery
import com.mumbicodes.network.SearchCharacterQuery
import com.mumbicodes.network.type.MediaFormat
import com.mumbicodes.network.type.MediaSort
import com.mumbicodes.network.type.MediaType
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun searchAnime(
        searchParam: String,
        type: MediaType,
        sortList: List<MediaSort>,
        formatIn: List<MediaFormat>
    ): Flow<List<SearchAnimeQuery.Medium>>

    fun searchCharacter(searchParam: String): Flow<List<SearchCharacterQuery.Character>>
}