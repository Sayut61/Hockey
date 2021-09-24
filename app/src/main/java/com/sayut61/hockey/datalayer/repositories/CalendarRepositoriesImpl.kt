package com.sayut61.hockey.datalayer.repositories

import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSource
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.calendar.CalendarResponse
import com.sayut61.hockey.domain.CalendarRepositories
import com.sayut61.hockey.domain.entities.Calendar
import javax.inject.Inject

class CalendarRepositoriesImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): CalendarRepositories {
    override suspend fun getCalendarInfo(): List<Calendar> {
        val infoByGame = remoteDataSource.getInfoByGameDay()
        return infoByGame.map {
            Calendar(
                gameDate = it.gameDate,
                linkOnDetailInfoByGame = it.content.linkOnDetailInfoByGame,
                awayTeamName = it.teams.away.team.awayTeamName,
                awayTeamId = it.teams.away.team.awayTeamId,
                homeTeamName = it.teams.home.team.homeTeamName,
                homeTeamId = it.teams.home.team.homeTeamId
            )
        }
    }
}