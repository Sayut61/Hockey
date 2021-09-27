package com.sayut61.hockey.datalayer.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSource
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.calendar.CalendarResponse
import com.sayut61.hockey.domain.CalendarRepositories
import com.sayut61.hockey.domain.entities.Calendar
import java.time.LocalDate
import javax.inject.Inject

class CalendarRepositoriesImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): CalendarRepositories {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getCalendarInfo(date: LocalDate): List<Calendar> {
        val infoByGame = remoteDataSource.getInfoByGameDay(date)
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