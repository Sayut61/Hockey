package com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams

import com.google.gson.annotations.SerializedName
import java.lang.Exception

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

fun teamFullInfoFromFirstApiResponseToFullInfoByTeams(teams: TeamFullInfoFromFirstApiResponse): FullInfoByTeam {
    val resultList = teams.teams.flatMap { fullInfo ->
        fullInfo.teamStats.flatMap { it.splits}.map { stats ->
            FullInfoByTeam(
                id = fullInfo.id,
                teamFullName = fullInfo.teamFullName,
                teamShortName = fullInfo.teamShortName,
                firstYearOfPlay = fullInfo.firstYearOfPlay,
                gamesPlayed = stats.statByNumbers.gamesPlayed,
                wins = stats.statByNumbers.wins,
                losses = stats.statByNumbers.losses,
                pts = stats.statByNumbers.pts,
                goalsPerGame = stats.statByNumbers.goalsPerGame,
                goalsAgainstPerGame = stats.statByNumbers.goalsAgainstPerGame,
                powerPlayPercentage = stats.statByNumbers.powerPlayPercentage,
                powerPlayGoals = stats.statByNumbers.powerPlayGoals,
                powerPlayGoalsAgainst = stats.statByNumbers.powerPlayGoalsAgainst,
                powerPlayOpportunities = stats.statByNumbers.powerPlayOpportunities,
                shotsPerGame = stats.statByNumbers.shotsPerGame,
                shotsAllowed = stats.statByNumbers.shotsPerGame,
                placeOnWins = stats.statByPlaces.placeOnWins,
                placeOnLosses = stats.statByPlaces.placeOnLosses,
                placeOnPts = stats.statByPlaces.placeOnPts,
                placeGoalsPerGame = stats.statByPlaces.placeGoalsPerGame,
                placeGoalsAgainstPerGame = stats.statByPlaces.placeGoalsAgainstPerGame
            )
        }
    }
    return if(resultList.isNotEmpty())
        resultList[0]
    else
        throw Exception("error get teams info")
}

/*    val teamInfo = mutableListOf<FullInfoByTeam>()
    for (fullInfo in team.teams){
        for (it in fullInfo.teamStats){
            for (stats in it.splits){
                val team = FullInfoByTeam(
                    id = fullInfo.id,
                    teamFullName = fullInfo.teamFullName,
                    teamShortName = fullInfo.teamShortName,
                    firstYearOfPlay = fullInfo.firstYearOfPlay,
                    gamesPlayed = stats.statByNumbers.gamesPlayed,
                    wins = stats.statByNumbers.wins,
                    losses = stats.statByNumbers.losses,
                    pts = stats.statByNumbers.pts,
                    goalsPerGame = stats.statByNumbers.goalsPerGame,
                    goalsAgainstPerGame = stats.statByNumbers.goalsAgainstPerGame,
                    powerPlayPercentage = stats.statByNumbers.powerPlayPercentage,
                    powerPlayGoals = stats.statByNumbers.powerPlayGoals,
                    powerPlayGoalsAgainst = stats.statByNumbers.powerPlayGoalsAgainst,
                    powerPlayOpportunities = stats.statByNumbers.powerPlayOpportunities,
                    shotsPerGame = stats.statByNumbers.shotsPerGame,
                    shotsAllowed = stats.statByNumbers.shotsPerGame,
                    placeOnWins = stats.statByPlaces.placeOnWins,
                    placeOnLosses = stats.statByPlaces.placeOnLosses,
                    placeOnPts = stats.statByPlaces.placeOnPts,
                    placeGoalsPerGame = stats.statByPlaces.placeGoalsPerGame,
                    placeGoalsAgainstPerGame = stats.statByPlaces.placeGoalsAgainstPerGame
                )
                teamInfo.add(team)
            }
        }
    }
    return teamInfo
}*/

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
    val teamStats: List<Stats>
)

data class Stats(
    val splits: List<StatsByNumbersAndPlaces>
)

data class StatsByNumbersAndPlaces(
    @SerializedName("stat")
    val statByNumbers: StatByNumbers,
    @SerializedName("stat")
    val statByPlaces: StatByPlaces
)

data class StatByNumbers(
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
)

data class StatByPlaces(
    @SerializedName("wins")
    val placeOnWins: String,
    @SerializedName("losses")
    val placeOnLosses: String,
    @SerializedName("pts")
    val placeOnPts: String,
    @SerializedName("goalsPerGame")
    val placeGoalsPerGame: String,
    @SerializedName("goalsAgainstPerGame")
    val placeGoalsAgainstPerGame: String
)