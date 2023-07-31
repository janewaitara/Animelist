package com.mumbicodes.model.data

data class Anime(
    val bannerImage: String?,
    val coverImage: String?,
    val title: AnimeTitle?,
    val description: String?,
    val duration: Int?,
    val genres: List<String?>?,
    val type: MediaType?,
    val trailer: MediaTrailer?,
    val episodes: Int?,
    val format: MediaFormat?,
    val characters: List<Character?>?,
    val recommendations: List<Anime?>?,
    val studios: List<String?>?
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