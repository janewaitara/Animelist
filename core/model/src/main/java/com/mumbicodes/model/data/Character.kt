package com.mumbicodes.model.data

data class Character(
    val id: Int,
    val name: CharacterName?,
    val description: String?,
    val age: Int?,
    val gender: String?,
    val image: String?,
    val dateOfBirth: DateOfBirth?,
    val animes: List<Anime?>?
)

data class CharacterName(
    val full: String,
    val native: String
)

data class DateOfBirth(
    val year: Int,
    val month: Int,
    val day: Int
)