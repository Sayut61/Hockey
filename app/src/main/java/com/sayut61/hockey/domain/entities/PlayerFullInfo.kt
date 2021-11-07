package com.sayut61.hockey.domain.entities

data class PlayerFullInfo(
    val playerId: Int,
    val fullName: String,
    val playerLink: String,
    val playerNumber: String,
    val birthDate: String,
    val currentAge: Int,
    val birthCity: String,
    val nationality: String,
    val shootsCatches: String,
    val teamId: Int,
    val teamFullName: String,
    val wing: String,
    val position: String,
    val teamLogo: String?,
    val playerPhoto: String?
)