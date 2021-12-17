package com.sayut61.hockey.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
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
    val powerPlayPercentage: String,
    val powerPlayGoals: Double,
    val powerPlayGoalsAgainst: Double,
    val powerPlayOpportunities: Double,
    val shotsPerGame: Int,
    val shotsAllowed: Int,
    val placeOnWins: String,
    val placeOnLosses: String,
    val placeOnPts: String,
    val placeGoalsPerGame: String,
    val placeGoalsAgainstPerGame: String
) : Parcelable