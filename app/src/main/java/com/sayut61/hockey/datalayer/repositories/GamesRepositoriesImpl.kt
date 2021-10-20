package com.sayut61.hockey.datalayer.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.GamesInfoDao
import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSource
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.TeamsInfo.TeamInfoFromSecondApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.calendar.GameFromFirstApi
import com.sayut61.hockey.domain.GamesRepositories
import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import java.time.LocalDate
import javax.inject.Inject

class GamesRepositoriesImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val gamesInfoDao: GamesInfoDao
) : GamesRepositories {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getGamesGeneralInfo(date: LocalDate): List<GameGeneralInfo> {
        val games: List<GameFromFirstApi> = remoteDataSource.getGamesByDate(date)
        val logosFromSecondApi: List<TeamInfoFromSecondApi> = remoteDataSource.getTeamsSecondApi()
        return games.map { game ->
            val isInDb = gamesInfoDao.getAllInfo().find { it.gameId == game.gameId } != null
            val homeTeamLogo =
                logosFromSecondApi.find { logoFromApi -> game.homeTeamNameFull.endsWith(logoFromApi.shortName) }
            val awayTeamLogo =
                logosFromSecondApi.find { logoFromApi -> game.awayTeamNameFull.endsWith(logoFromApi.shortName) }
            GameGeneralInfo(
                gameDate = game.gameDate,
                linkOnDetailInfoByGame = game.linkOnDetailInfoByGame,
                awayTeamNameFull = game.awayTeamNameFull,
                awayTeamId = game.awayTeamId,
                homeTeamNameFull = game.homeTeamNameFull,
                homeTeamId = game.homeTeamId,
                gameId = game.gameId,
                homeTeamLogo = homeTeamLogo?.wikipediaLogoUrl,
                awayTeamLogo = awayTeamLogo?.wikipediaLogoUrl,
                isInFavoriteGame = isInDb
            )
        }
    }
    override suspend fun getGameFullInfo(gameGeneralInfo: GameGeneralInfo): GameFullInfo {
        val fullInfo = remoteDataSource.getGameDetails(gameGeneralInfo.linkOnDetailInfoByGame)
        return GameFullInfo(
            generalInfo = gameGeneralInfo,
//            players = fullInfo.players,
            currentPeriod = fullInfo.currentPeriod,
            currentPeriodOrdinal = fullInfo.currentPeriodOrdinal,
            currentPeriodTimeRemaining = fullInfo.currentPeriodTimeRemaining,
            periods = fullInfo.periods,
            homeTeamGoalsByPeriods = fullInfo.homeTeamGoalsByPeriods,
            awayTeamGoalsByPeriods = fullInfo.awayTeamGoalsByPeriods,
            homeTeamShotsOnGoalByPeriods = fullInfo.homeTeamShotsOnGoalByPeriods,
            awayTeamShotsOnGoalByPeriods = fullInfo.awayTeamShotsOnGoalByPeriods,
            goalsAwayTeam = fullInfo.goalsAwayTeam,
            goalsHomeTeam = fullInfo.goalsHomeTeam,
            shotsHomeTeam = fullInfo.shotsHomeTeam,
            shotsAwayTeam = fullInfo.shotsAwayTeam,
            blockedAwayTeam = fullInfo.blockedAwayTeam,
            blockedHomeTeam = fullInfo.blockedHomeTeam,
            hitsHomeTeam = fullInfo.hitsHomeTeam,
            hitsAwayTeam = fullInfo.hitsAwayTeam,
            codedGameState = fullInfo.codedGameState
        )
    }
}