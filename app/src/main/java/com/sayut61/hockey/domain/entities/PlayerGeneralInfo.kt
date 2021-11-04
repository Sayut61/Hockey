package com.sayut61.hockey.domain.entities


data class PlayerGeneralInfo(
    val teamId: Int,
    val teamFullName: String,
    val teamShortName: String,
    val jerseyNumber: Int,
    val playerId: Int,
    val fullName: String,
    val linkOnPlayerDetailInfo: String,
    val logo: String?
)