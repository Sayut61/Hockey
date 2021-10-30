package com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.games

import com.google.gson.annotations.SerializedName

data class GameFromFirstApi(
    val gameId: Int,
    val gameDate: String,
    val linkOnDetailInfoByGame: String,
    val awayTeamNameFull: String,
    val awayTeamId: Int,
    val homeTeamNameFull: String,
    val homeTeamId: Int
)

fun gamesResponseToGamesFromFirstApi(gamesResponse: GamesResponse): List<GameFromFirstApi> {
    return gamesResponse.dates.flatMap { it.games }.map {
        GameFromFirstApi(
            gameId = it.gameId,
            gameDate = it.gameDate,
            linkOnDetailInfoByGame = it.linkOnDetailInfoByGame,
            awayTeamId = it.teams.away.team.awayTeamId,
            homeTeamId = it.teams.home.team.homeTeamId,
            awayTeamNameFull = it.teams.away.team.awayTeamNameFull,
            homeTeamNameFull = it.teams.home.team.homeTeamNameFull
        )
    }
}
data class GamesResponse(
    val dates: List<DateByGames>
)
data class DateByGames(
    val games: List<Games>
)
data class Games(
    @SerializedName("gamePk")
    val gameId: Int,
    val gameDate: String,
    @SerializedName("link")
    val linkOnDetailInfoByGame: String,
    val teams: HomeOrAway
    )
data class HomeOrAway(
    val away: AwayTeam,
    val home: HomeTeam
)
data class AwayTeam(
    val team: AwayTeamName
)
data class HomeTeam(
    val team: HomeTeamName
)
data class AwayTeamName(
    @SerializedName("name")
    val awayTeamNameFull: String,
    @SerializedName("id")
    val awayTeamId: Int
)
data class HomeTeamName(
    @SerializedName("name")
    val homeTeamNameFull: String,
    @SerializedName("id")
    val homeTeamId: Int
)


