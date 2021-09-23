package com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.calendar

data class CalendarResponse(
    val totalItems: Int,
    val dates: List<DateByGames>
)
data class DateByGames(
    val games: List<InfoByGame>
)
data class InfoByGame(
    val gameDate: String,
    val content: LinkByGame
    )
data class LinkByGame(
    val link: String
)
