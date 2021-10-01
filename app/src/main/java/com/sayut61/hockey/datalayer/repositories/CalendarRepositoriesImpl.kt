package com.sayut61.hockey.datalayer.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.GamesInfoDao
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.dto.FavoriteGame
import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSource
import com.sayut61.hockey.domain.CalendarRepositories
import com.sayut61.hockey.domain.entities.Calendar
import java.time.LocalDate
import javax.inject.Inject

class CalendarRepositoriesImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val gamesInfoDao: GamesInfoDao
): CalendarRepositories {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getCalendarInfo(date: LocalDate): List<Calendar> {
        val infoByGame = remoteDataSource.getInfoByGameDay(date)
        return infoByGame.map {infoByGame->
            val isInDb = gamesInfoDao.getAllInfo().find { it.gameId == infoByGame.gameId }!=null
            Calendar(
                gameDate = infoByGame.gameDate,
                linkOnDetailInfoByGame = infoByGame.content.linkOnDetailInfoByGame,
                awayTeamName = infoByGame.teams.away.team.awayTeamName,
                awayTeamId = infoByGame.teams.away.team.awayTeamId,
                homeTeamName = infoByGame.teams.home.team.homeTeamName,
                homeTeamId = infoByGame.teams.home.team.homeTeamId,
                gameId = infoByGame.gameId,
                isInFavorite = isInDb
            )
        }
    }

    override suspend fun addToFavorite(calendar: Calendar) {
        gamesInfoDao.insert(FavoriteGame(calendar.gameId))
    }

    override suspend fun removeFromFavorite(calendar: Calendar) {
        gamesInfoDao.delete(FavoriteGame(calendar.gameId))
    }
}