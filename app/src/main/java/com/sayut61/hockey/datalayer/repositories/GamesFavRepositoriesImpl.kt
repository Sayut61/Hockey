package com.sayut61.hockey.datalayer.repositories

import com.sayut61.hockey.datalayer.datasource.loacaldatasource.GamesInfoDao
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.dto.FavoriteGame
import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSource
import com.sayut61.hockey.domain.GamesFavRepositories
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import javax.inject.Inject

class GamesFavRepositoriesImpl @Inject constructor(
    val gamesInfoDao: GamesInfoDao
): GamesFavRepositories {

    override suspend fun getFavoriteGames(): List<GameGeneralInfo> {
        val listFavoriteGames = gamesInfoDao.getAllInfo()
        return listFavoriteGames.map {
            favoriteGameToGame(it)
        }
    }
    override suspend fun removeFromFavoriteGame(gameGeneralInfo: GameGeneralInfo) {
        gamesInfoDao.delete(gameToFavoriteGame(gameGeneralInfo))
    }
    override suspend fun addToFavoriteGame(gameGeneralInfo: GameGeneralInfo) {
        gamesInfoDao.insert(gameToFavoriteGame(gameGeneralInfo))
    }
    fun gameToFavoriteGame(gameGeneralInfo: GameGeneralInfo): FavoriteGame{
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
    fun favoriteGameToGame(favoriteGame: FavoriteGame): GameGeneralInfo{
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