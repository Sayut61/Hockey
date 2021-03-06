package com.sayut61.hockey.datalayer.repositories

import com.sayut61.hockey.datalayer.datasource.loacaldatasource.GamesInfoDao
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.dto.FavoriteGame
import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSourceImpl
import com.sayut61.hockey.domain.GamesFavoriteRepository
import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import com.sayut61.hockey.domain.flow.LoadingResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GamesFavoriteRepositoryImpl @Inject constructor(
    val gamesInfoDao: GamesInfoDao,
    val remoteDataSource: RemoteDataSourceImpl
) : GamesFavoriteRepository {

    var cacheFavoriteGame: List<GameFullInfo>? = null
    override fun getFavoriteGames(): Flow<List<GameFullInfo>> = flow {
        cacheFavoriteGame?.let {
            emit(it)
        }
        val listFavoriteGames = gamesInfoDao.getAllInfo()
        val result = listFavoriteGames.map { favoriteGame ->
            val gameFromApi =
                remoteDataSource.getGameDetails(favoriteGame.linkOnDetailInfoByGame)
            GameFullInfo(
                generalInfo = favoriteGameToGameGeneralInfo(favoriteGame),
                currentPeriod = gameFromApi.currentPeriod,
                currentPeriodOrdinal = gameFromApi.currentPeriodOrdinal,
                currentPeriodTimeRemaining = gameFromApi.currentPeriodTimeRemaining,
                periods = gameFromApi.periods,
                homeTeamGoalsByPeriods = gameFromApi.homeTeamGoalsByPeriods,
                awayTeamGoalsByPeriods = gameFromApi.awayTeamGoalsByPeriods,
                homeTeamShotsOnGoalByPeriods = gameFromApi.homeTeamShotsOnGoalByPeriods,
                awayTeamShotsOnGoalByPeriods = gameFromApi.awayTeamShotsOnGoalByPeriods,
                goalsAwayTeam = gameFromApi.goalsAwayTeam,
                goalsHomeTeam = gameFromApi.goalsHomeTeam,
                shotsHomeTeam = gameFromApi.shotsHomeTeam,
                shotsAwayTeam = gameFromApi.shotsAwayTeam,
                blockedAwayTeam = gameFromApi.blockedAwayTeam,
                blockedHomeTeam = gameFromApi.blockedHomeTeam,
                hitsHomeTeam = gameFromApi.hitsHomeTeam,
                hitsAwayTeam = gameFromApi.hitsAwayTeam,
                gameState = getStatusByNumber(gameFromApi.codedGameState),
                playersAwayTeam = gameFromApi.playersAwayTeam,
                playersHomeTeam = gameFromApi.playersHomeTeam
            )
        }
        cacheFavoriteGame = result
        emit(result)
    }

    override suspend fun removeFromFavoriteGame(gameGeneralInfo: GameGeneralInfo) {
        gamesInfoDao.delete(gameToFavoriteGame(gameGeneralInfo))
    }

    override suspend fun addToFavoriteGame(gameGeneralInfo: GameGeneralInfo) {
        gamesInfoDao.insert(gameToFavoriteGame(gameGeneralInfo))
    }

    fun gameToFavoriteGame(gameGeneralInfo: GameGeneralInfo): FavoriteGame {
        return FavoriteGame(
            gameGeneralInfo.gameDate,
            gameGeneralInfo.linkOnDetailInfoByGame,
            gameGeneralInfo.awayTeamNameFull,
            gameGeneralInfo.awayTeamId,
            gameGeneralInfo.homeTeamNameFull,
            gameGeneralInfo.homeTeamId,
            gameGeneralInfo.gameId,
            gameGeneralInfo.homeTeamLogo,
            gameGeneralInfo.awayTeamLogo,
        )
    }

    fun favoriteGameToGameGeneralInfo(favoriteGame: FavoriteGame): GameGeneralInfo {
        return GameGeneralInfo(
            favoriteGame.gameDate,
            favoriteGame.linkOnDetailInfoByGame,
            favoriteGame.awayTeamNameFull,
            favoriteGame.awayTeamId,
            favoriteGame.homeTeamNameFull,
            favoriteGame.homeTeamId,
            favoriteGame.gameId,
            favoriteGame.homeTeamLogo,
            favoriteGame.awayTeamLogo,
            true
        )
    }
}