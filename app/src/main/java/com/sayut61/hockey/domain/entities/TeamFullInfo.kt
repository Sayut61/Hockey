package com.sayut61.hockey.domain.entities

data class TeamFullInfo(
    val teamGeneralInfo: TeamGeneralInfo,
    val id: Int,
    val teamFullName: String,
    val teamShortName: String,
    val firstYearOfPlay: Int,
    val gamesPlayed: Int,
    val wins: Int,
    val losses: Int,
    val pts: Int,
    val goalsPerGame: Double,
    val goalsAgainstPerGame: Double,
    val powerPlayPercentage: Any,
    val powerPlayGoals: Double,
    val powerPlayGoalsAgainst: Double,
    val powerPlayOpportunities: Double,
    val shotsPerGame: Double,
    val shotsAllowed: Double,
    val placeOnWins: String,
    val placeOnLosses: String,
    val placeOnPts: String,
    val placeGoalsPerGame: String,
    val placeGoalsAgainstPerGame: String
)