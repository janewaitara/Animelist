package com.mumbicodes.model.data

import com.mumbicodes.network.AnimeQuery
import com.mumbicodes.network.CharacterQuery
import com.mumbicodes.network.RecommendationsQuery
import com.mumbicodes.network.SearchAnimeQuery
import com.mumbicodes.network.type.MediaFormat
import com.mumbicodes.network.type.MediaType

data class Anime(
    val id: Int,
    val bannerImage: String? = null,
    val coverImage: String? = null,
    val title: AnimeTitle?,
    val description: String? = "",
    val duration: Int? = 0,
    val genres: List<String?>? = emptyList(),
    val type: LocalMediaType? = null,
    val trailer: MediaTrailer? = null,
    val episodes: Int? = 0,
    val format: LocalMediaFormat? = null,
    val characters: List<Character?>? = emptyList(),
    val recommendations: List<Anime?>? = emptyList(),
    val studios: List<String?>? = emptyList()
)

data class AnimeTitle(
    val native: String?,
    val english: String?,
    val romaji: String?
)

enum class LocalMediaType {
    ANIME, MANGA
}

data class MediaTrailer(
    val id: String?,
    val site: String?,
    val thumbnail: String?
)

enum class LocalMediaFormat {
    TV,
    TV_SHORT,
    MOVIE,
    SPECIAL,
    OVA,
    ONA,
    MUSIC,
    MANGA,
    NOVEL,
    ONE_SHOT,
    UNKNOWN
}

/**
 * Mappers
 * */
fun SearchAnimeQuery.Medium.toModelAnime() = Anime(
    id = this.id,
    coverImage = this.coverImage?.medium,
    title = this.title?.toAnimeTitle(),
    episodes = this.episodes,
    duration = this.duration,
    format = this.format?.toLocalMediaFormat()
)

fun AnimeQuery.Media.toModelAnime() = Anime(
    id = this.id,
    bannerImage = this.bannerImage,
    coverImage = this.coverImage?.medium,
    title = this.title?.toAnimeTitle(),
    description = this.description,
    duration = this.duration,
    genres = this.genres,
    type = this.type?.toLocalMediaType(),
    trailer = this.trailer?.toMediaTrailer(),
    episodes = this.episodes,
    format = this.format?.toLocalMediaFormat(),
    characters = this.characters?.nodes?.map {
        it?.toCharacter()
    },
    recommendations = this.recommendations?.nodes?.map {
        it?.media?.toAnime()
    },
    studios = this.studios?.nodes?.map {
        it?.name
    }
)

fun RecommendationsQuery.Media.toModelAnime() = Anime(
    id = this.id,
    title = this.title?.toAnimeTitle(),
    type = this.type?.toLocalMediaType(),
    coverImage = this.coverImage?.medium,
    episodes = this.episodes,
    duration = this.duration
)

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

private fun SearchAnimeQuery.Title.toAnimeTitle() = AnimeTitle(
    native = this.native,
    english = this.english,
    romaji = null
)

private fun AnimeQuery.Title.toAnimeTitle() = AnimeTitle(
    native = this.native,
    english = this.english,
    romaji = null
)

private fun AnimeQuery.Trailer.toMediaTrailer() = MediaTrailer(
    id = this.id,
    site = this.site,
    thumbnail = this.thumbnail
)

private fun MediaFormat.toLocalMediaFormat(): LocalMediaFormat = when (this) {
    MediaFormat.TV -> LocalMediaFormat.TV
    MediaFormat.TV_SHORT -> LocalMediaFormat.TV_SHORT
    MediaFormat.MOVIE -> LocalMediaFormat.MOVIE
    MediaFormat.SPECIAL -> LocalMediaFormat.SPECIAL
    MediaFormat.OVA -> LocalMediaFormat.OVA
    MediaFormat.ONA -> LocalMediaFormat.ONA
    MediaFormat.MUSIC -> LocalMediaFormat.MUSIC
    MediaFormat.MANGA -> LocalMediaFormat.MANGA
    MediaFormat.NOVEL -> LocalMediaFormat.NOVEL
    MediaFormat.ONE_SHOT -> LocalMediaFormat.ONE_SHOT
    MediaFormat.UNKNOWN__ -> LocalMediaFormat.UNKNOWN
}

private fun MediaType.toLocalMediaType(): LocalMediaType = when (this) {
    MediaType.ANIME -> LocalMediaType.ANIME
    MediaType.MANGA -> LocalMediaType.MANGA
    MediaType.UNKNOWN__ -> LocalMediaType.ANIME
}

internal fun AnimeQuery.Media1.toAnime() = Anime(
    id = this.id,
    bannerImage = this.bannerImage,
    title = this.title?.toAnimeTitle()
)

private fun AnimeQuery.Title1.toAnimeTitle() = AnimeTitle(
    native = this.native,
    english = this.english,
    romaji = null
)

private fun RecommendationsQuery.Title.toAnimeTitle() = AnimeTitle(
    native = this.native,
    english = this.english,
    romaji = null
)