package com.sayut61.hockey.datalayer.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.GamesInfoDao
import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSource
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.TeamsInfo.TeamInfoFromSecondApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.calendar.Games
import com.sayut61.hockey.domain.GamesRepositories
import com.sayut61.hockey.domain.entities.Game
import java.time.LocalDate
import javax.inject.Inject

class GamesRepositoriesImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val gamesInfoDao: GamesInfoDao
): GamesRepositories {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getGamesInfo(date: LocalDate): List<Game> {
        val games: List<Games> = remoteDataSource.getGamesByDate(date)
        val logosFromSecondApi: List<TeamInfoFromSecondApi> = remoteDataSource.getTeamsSecondApi()
        return games.map { game->
            val isInDb = gamesInfoDao.getAllInfo().find { it.gameId == game.gameId }!=null
            val homeTeamLogo = logosFromSecondApi.find { logoFromApi->game.teams.home.team.homeTeamNameFull.endsWith(logoFromApi.shortName) }
            val awayTeamLogo = logosFromSecondApi.find { logoFromApi->game.teams.away.team.awayTeamNameFull.endsWith(logoFromApi.shortName) }
            Game(
                gameDate = game.gameDate,
                linkOnDetailInfoByGame = game.content.linkOnDetailInfoByGame,
                awayTeamNameFull = game.teams.away.team.awayTeamNameFull,
                awayTeamId = game.teams.away.team.awayTeamId,
                homeTeamNameFull = game.teams.home.team.homeTeamNameFull,
                homeTeamId = game.teams.home.team.homeTeamId,
                gameId = game.gameId,
                homeTeamLogo = homeTeamLogo?.WikipediaLogoUrl,
                awayTeamLogo = awayTeamLogo?.WikipediaLogoUrl,
                isInFavoriteGame = isInDb
            )
        }
    }
}