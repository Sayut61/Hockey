package com.sayut61.hockey.datalayer.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.GamesInfoDao
import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSourceImpl
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams.TeamGeneralInfoFromSecondApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.games.GameFromFirstApi
import com.sayut61.hockey.domain.GamesRepository
import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSourceImpl,
    private val gamesInfoDao: GamesInfoDao
) : GamesRepository {
    var cacheGames: MutableMap<LocalDate, List<GameFullInfo>> = mutableMapOf()

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getGamesFullInfo(date: LocalDate): Flow<List<GameFullInfo>> = flow {
        var cacheGamesOnDate = cacheGames[date]
        cacheGamesOnDate?.let {
            emit(it)
        }
        val games: List<GameFromFirstApi> = remoteDataSource.getGamesByDate(date)
        val logosFromSecondApiGeneral: List<TeamGeneralInfoFromSecondApi> =
            remoteDataSource.getAllTeamsFromSecondApi()
        val result = games.map { game ->
            val isInDb = gamesInfoDao.getAllInfo().find { it.gameId == game.gameId } != null
            val homeTeamLogo = logosFromSecondApiGeneral.find { logoFromApi ->
                game.homeTeamNameFull.endsWith(logoFromApi.shortName)
            }
            val awayTeamLogo = logosFromSecondApiGeneral.find { logoFromApi ->
                game.awayTeamNameFull.endsWith(logoFromApi.shortName)
            }
            val generalInfo = GameGeneralInfo(
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
            getGameFullInfo(generalInfo)
        }
        cacheGamesOnDate = result
        emit(result)

//        var equals = true
//        for (i in 0..result.lastIndex)
//            if (result[i] != cacheGames[date]?.get(i)) {
//                equals = false
//                break
//            }
//        if (!equals) {
//            cacheGames.put(date, result)
//            emit(result)
//        }
    }


    override suspend fun getGameFullInfo(gameGeneralInfo: GameGeneralInfo): GameFullInfo {
        val fullInfo =
            remoteDataSource.getGameDetails(gameGeneralInfo.linkOnDetailInfoByGame)
        return GameFullInfo(
            generalInfo = gameGeneralInfo,
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
            gameState = getStatusByNumber(fullInfo.codedGameState),
            playersAwayTeam = fullInfo.playersAwayTeam,
            playersHomeTeam = fullInfo.playersHomeTeam
        )
    }
}