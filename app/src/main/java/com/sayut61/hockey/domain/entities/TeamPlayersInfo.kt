package com.sayut61.hockey.domain.entities

data class TeamPlayersInfo(
    val jerseyNumber: Int,
    val playerId: Int,
    val fullName: String,
    val linkOnFullInfoByPlayer: String,
    val type: String
)