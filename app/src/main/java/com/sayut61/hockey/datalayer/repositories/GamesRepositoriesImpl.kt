package com.sayut61.hockey.datalayer.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.GamesInfoDao
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.dto.FavoriteGame
import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSource
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.TeamsInfo.TeamsInfoFromSecondAPI
import com.sayut61.hockey.domain.GamesRepositories
import com.sayut61.hockey.domain.entities.Game
import java.time.LocalDate
import javax.inject.Inject

class GamesRepositoriesImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val gamesInfoDao: GamesInfoDao
): GamesRepositories {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getGameInfo(date: LocalDate): List<Game> {
        val games = remoteDataSource.getGamesByDate(date)
        val logosFromSecondApi: List<TeamsInfoFromSecondAPI> = remoteDataSource.getAllTeamsInfo()
        return games.map { game->
            val isInDb = gamesInfoDao.getAllInfo().find { it.gameId == game.gameId }!=null
            val homeTeamLogo = logosFromSecondApi.find { logoFromApi->game.teams.home.team.homeTeamName.endsWith(logoFromApi.Name) }
            val awayTeamLogo = logosFromSecondApi.find { logoFromApi->game.teams.away.team.awayTeamName.endsWith(logoFromApi.Name) }
            Game(
                gameDate = game.gameDate,
                linkOnDetailInfoByGame = game.content.linkOnDetailInfoByGame,
                awayTeamName = game.teams.away.team.awayTeamName,
                awayTeamId = game.teams.away.team.awayTeamId,
                homeTeamName = game.teams.home.team.homeTeamName,
                homeTeamId = game.teams.home.team.homeTeamId,
                gameId = game.gameId,
                homeTeamLogo = homeTeamLogo?.WikipediaLogoUrl,
                awayTeamLogo = awayTeamLogo?.WikipediaLogoUrl,
                isInFavorite = isInDb
            )
        }
    }
}