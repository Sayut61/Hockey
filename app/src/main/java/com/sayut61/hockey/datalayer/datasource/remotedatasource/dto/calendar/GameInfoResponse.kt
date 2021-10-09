package com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.calendar

import com.google.gson.annotations.SerializedName

data class GamesDateResponse(
    val dates: List<DateByGames>
)
data class DateByGames(
    val games: List<Games>
)
data class Games(
    @SerializedName("gamePk")
    val gameId: Int,
    val gameDate: String,
    val content: LinkByGame,
    val teams: HomeOrAway
    )
data class LinkByGame(
    @SerializedName("link")
    val linkOnDetailInfoByGame: String
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

