package com.mumbicodes.model.data

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
    val year: Int,
    val month: Int,
    val day: Int
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

private fun SearchCharacterQuery.Name.toCharacterName() = CharacterName(
    full = this.full,
    native = this.native
)