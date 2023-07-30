package com.mumbicodes.model.data

data class Character(
    val id: Int,
    val name: CharacterName,
    val description: String,
    val age: Int,
    val gender: String,
    val dateOfBirth: DateOfBirth,
    val image: String
)
// TODO add animes nodes

data class CharacterName(
    val full: String,
    val native: String
)

data class DateOfBirth(
    val year: Int,
    val month: Int,
    val day: Int
)