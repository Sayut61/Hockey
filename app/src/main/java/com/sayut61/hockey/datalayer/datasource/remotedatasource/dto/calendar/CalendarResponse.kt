package com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.calendar

import com.google.gson.annotations.SerializedName

data class CalendarResponse(
    val dates: List<DateByGames>
)
data class DateByGames(
    val games: List<InfoByGame>
)
data class InfoByGame(
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
    val awayTeamName: String,
    @SerializedName("id")
    val awayTeamId: Int
)
data class HomeTeamName(
    @SerializedName("name")
    val homeTeamName: String,
    @SerializedName("id")
    val homeTeamId: Int
)

