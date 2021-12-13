package com.sayut61.hockey.datalayer.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.GamesInfoDao
import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSource
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams.TeamGeneralInfoFromSecondApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.games.GameFromFirstApi
import com.sayut61.hockey.domain.GameRepository
import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import com.sayut61.hockey.domain.flow.LoadingResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val gamesInfoDao: GamesInfoDao
) : GameRepository {
    var cacheGame: List<GameGeneralInfo>? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getGamesGeneralInfo(date: LocalDate): Flow<LoadingResult<List<GameGeneralInfo>>> =
        flow {
            emit(LoadingResult.Loading(true))
            try {
                cacheGame?.let {
                    emit(LoadingResult.SuccessResult(it))
                    emit(LoadingResult.Loading(false))
                }
                val games: List<GameFromFirstApi> = remoteDataSource.getGamesByDate(date)
                val logosFromSecondApiGeneral: List<TeamGeneralInfoFromSecondApi> =
                    remoteDataSource.getTeamsSecondApi()
                val result = games.map { game ->
                    val isInDb = gamesInfoDao.getAllInfo().find { it.gameId == game.gameId } != null
                    val homeTeamLogo = logosFromSecondApiGeneral.find { logoFromApi ->
                        game.homeTeamNameFull.endsWith(logoFromApi.shortName)
                    }
                    val awayTeamLogo = logosFromSecondApiGeneral.find { logoFromApi ->
                        game.awayTeamNameFull.endsWith(logoFromApi.shortName)
                    }
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

                var equals = true
                for (i in 0..result.lastIndex)
                    if (result[i] != cacheGame?.get(i)) {
                        equals = false
                        break
                    }
                if (!equals) {
                    cacheGame = result
                    emit(LoadingResult.SuccessResult(result))
                }
                emit(LoadingResult.Loading(false))
            } catch (ex: Exception) {
                emit(LoadingResult.ErrorResult(java.lang.Exception("Ошибка загрузки игр по выбранной дате")))
                emit(LoadingResult.Loading(false))
            }
        }

    var cacheGameFullInfo: GameFullInfo? = null
    override fun getGameFullInfo(gameGeneralInfo: GameGeneralInfo): Flow<LoadingResult<GameFullInfo>> =
        flow {
            emit(LoadingResult.Loading(true))
            try {
                cacheGameFullInfo?.let {
                    emit(LoadingResult.SuccessResult(it))
                    emit(LoadingResult.Loading(false))
                }
                val fullInfo =
                    remoteDataSource.getGameDetails(gameGeneralInfo.linkOnDetailInfoByGame)
                val result = GameFullInfo(
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
                var equals = true
                if (result != cacheGameFullInfo) {
                    equals = false
                }
                if (!equals) {
                    cacheGameFullInfo = result
                    emit(LoadingResult.SuccessResult(result))
                }
                emit(LoadingResult.Loading(false))
            } catch (ex: Exception) {
                emit(LoadingResult.ErrorResult(java.lang.Exception("Ошибка")))
                emit(LoadingResult.Loading(false))
            }
        }
}