package com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams

import com.google.gson.annotations.SerializedName
import java.lang.Exception
import kotlin.math.roundToInt

data class FullInfoByTeam(
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
    val shotsPerGame: Double,
    val shotsAllowed: Double,
    val placeOnWins: String,
    val placeOnLosses: String,
    val placeOnPts: String,
    val placeGoalsPerGame: String,
    val placeGoalsAgainstPerGame: String
)

fun teamFullInfoFromFirstApiResponseToFullInfoByTeams(teams: TeamFullInfoFromFirstApiResponse): FullInfoByTeam {
    val stats = teams.teams[0].teamStats[0].splits
    val statByNumbers = stats[0].stat
    val statByPlaces = stats[1].stat

   return FullInfoByTeam(
        id = teams.teams[0].id,
        teamFullName = teams.teams[0].teamFullName,
        teamShortName = teams.teams[0].teamShortName,
        firstYearOfPlay = teams.teams[0].firstYearOfPlay,
        gamesPlayed = statByNumbers.gamesPlayed ?: throw Exception("error get teams info"),
        wins = (statByNumbers.wins as? Double)?.roundToInt() ?: throw Exception("error get teams info"),
        losses = (statByNumbers.losses as? Double)?.roundToInt() ?: throw Exception("error get teams info"),
        pts = (statByNumbers.pts as? Double)?.roundToInt() ?: throw Exception("error get teams info"),
        goalsPerGame = (statByNumbers.goalsPerGame as? Double) ?: throw Exception("error get teams info"),
        goalsAgainstPerGame = (statByNumbers.goalsAgainstPerGame as? Double)?: throw Exception("error get teams info"),
        powerPlayPercentage = (statByNumbers.powerPlayPercentage as? String)?: throw Exception("error get teams info"),
        powerPlayGoals = (statByNumbers.powerPlayGoals as? Double)?: throw Exception("error get teams info"),
        powerPlayGoalsAgainst = (statByNumbers.powerPlayGoalsAgainst as? Double)?: throw Exception("error get teams info"),
        powerPlayOpportunities = (statByNumbers.powerPlayOpportunities as? Double)?: throw Exception("error get teams info"),
        shotsPerGame = (statByNumbers.shotsPerGame as? Double)?: throw Exception("error get teams info"),
        shotsAllowed = (statByNumbers.shotsAllowed as? Double)?: throw Exception("error get teams info"),
        placeOnWins = (statByPlaces.wins as? String)?: throw Exception("error get teams info"),
        placeOnLosses = (statByPlaces.losses as? String)?: throw Exception("error get teams info"),
        placeOnPts = (statByPlaces.pts as? String)?: throw Exception("error get teams info"),
        placeGoalsPerGame = (statByPlaces.goalsAgainstPerGame as? String)?: throw Exception("error get teams info"),
        placeGoalsAgainstPerGame = (statByPlaces.goalsAgainstPerGame as? String)?: throw Exception("error get teams info")
    )
}

data class TeamFullInfoFromFirstApiResponse(
    val teams: List<FullInfoByTeamResponse>
)

data class FullInfoByTeamResponse(
    val id: Int,
    @SerializedName("name")
    val teamFullName: String,
    @SerializedName("teamName")
    val teamShortName: String,
    val firstYearOfPlay: Int,
    val teamStats: List<Splits>
)

data class Splits(
    val splits: List<Stats>
)

data class Stats(
    val stat: StatInfo
)

data class StatInfo(
    val gamesPlayed: Int?,
    val wins: Any?,
    val losses: Any?,
    val pts: Any?,
    val goalsPerGame: Any?,
    val goalsAgainstPerGame: Any?,
    val powerPlayPercentage: Any?,
    val powerPlayGoals: Any?,
    val powerPlayGoalsAgainst: Any?,
    val powerPlayOpportunities: Any?,
    val shotsPerGame: Any?,
    val shotsAllowed: Any?,
)

