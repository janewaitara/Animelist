package com.mumbicodes.model.data

import com.mumbicodes.network.CharacterQuery

data class Anime(
    val id: Int,
    val bannerImage: String?,
    val coverImage: String?,
    val title: AnimeTitle?,
    val description: String?,
    val duration: Int? = 0,
    val genres: List<String?>? = emptyList(),
    val type: MediaType? = null,
    val trailer: MediaTrailer? = null,
    val episodes: Int? = 0,
    val format: MediaFormat? = null,
    val characters: List<Character?>? = emptyList(),
    val recommendations: List<Anime?>? = emptyList(),
    val studios: List<String?>? = emptyList()
)

data class AnimeTitle(
    val native: String?,
    val english: String?,
    val romaji: String?
)

enum class MediaType {
    ANIME, MANGA
}

data class MediaTrailer(
    val id: String?,
    val site: String?,
    val thumbnail: String?
)

enum class MediaFormat {
    TV,
    TV_SHORT,
    MOVIE,
    SPECIAL,
    OVA,
    ONA,
    MUSIC,
    MANGA,
    NOVEL,
    ONE_SHOT
}

// Mappers
internal fun CharacterQuery.Node.toAnime() = Anime(
    id = this.id,
    coverImage = this.coverImage?.medium,
    bannerImage = this.bannerImage,
    description = this.description,
    title = this.title?.toAnimeTitle()
)

private fun CharacterQuery.Title.toAnimeTitle() = AnimeTitle(
    native = this.native,
    english = this.english,
    romaji = null
)