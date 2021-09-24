package com.sayut61.hockey.domain.entities

import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.calendar.LinkByGame

class Calendar(
    val gameDate: String,
    val linkOnDetailInfoByGame: String,
    val awayTeamName: String,
    val awayTeamId: Int,
    val homeTeamName: String,
    val homeTeamId: Int
)