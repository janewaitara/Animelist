package com.mumbicodes.model.data

import com.mumbicodes.network.CharacterQuery
import com.mumbicodes.network.SearchCharacterQuery

data class Character(
    val id: Int,
    val name: CharacterName?,
    val description: String? = "",
    val age: String?,
    val gender: String?,
    val image: String?,
    val dateOfBirth: DateOfBirth? = null,
    val animes: List<Anime?>? = emptyList()
)

data class CharacterName(
    val full: String?,
    val native: String?
)

data class DateOfBirth(
    val year: Int?,
    val month: Int?,
    val day: Int?
)

fun SearchCharacterQuery.Character.toModelCharacter(): Character {
    return Character(
        id = this.id,
        name = this.name?.toCharacterName(),
        gender = this.gender,
        age = this.age,
        image = this.image?.medium
    )
}

/**
 * Used to map the Character query results to the Character in the model module
 * */
fun CharacterQuery.Character.toModelCharacter() = Character(
    id = this.id,
    name = this.name?.toCharacterName(),
    description = this.description,
    gender = this.gender,
    image = this.image?.large,
    age = this.age,
    dateOfBirth = this.dateOfBirth?.toDateOfBirth(),
    animes = this.media?.nodes?.map {
        it?.toAnime()
    }
)

private fun SearchCharacterQuery.Name.toCharacterName() = CharacterName(
    full = this.full,
    native = this.native
)
private fun CharacterQuery.Name.toCharacterName() = CharacterName(
    full = this.full,
    native = this.native
)

private fun CharacterQuery.DateOfBirth.toDateOfBirth() = DateOfBirth(
    year = this.year,
    month = this.month,
    day = this.day

)