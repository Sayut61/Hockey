package com.sayut61.hockey.datalayer.repositories

import com.sayut61.hockey.datalayer.datasource.loacaldatasource.GamesInfoDao
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.dto.FavoriteGame
import com.sayut61.hockey.domain.GamesFavRepositories
import com.sayut61.hockey.domain.entities.Game
import javax.inject.Inject

class GamesFavRepositoriesImpl @Inject constructor(
    val gamesInfoDao: GamesInfoDao
): GamesFavRepositories {

    override suspend fun getFavoriteGames(): List<Game> {
        val listFavoriteGames = gamesInfoDao.getAllInfo()
        return listFavoriteGames.map {
            favoriteGameToGame(it)
        }
    }
    override suspend fun removeFromFavoriteGame(game: Game) {
        gamesInfoDao.delete(gameToFavoriteGame(game))
    }
    override suspend fun addToFavoriteGame(game: Game) {
        gamesInfoDao.insert(gameToFavoriteGame(game))
    }
    private fun gameToFavoriteGame(game: Game): FavoriteGame{
        return FavoriteGame(
            game.gameDate,
            game.linkOnDetailInfoByGame,
            game.awayTeamNameFull,
            game.awayTeamId,
            game.homeTeamNameFull,
            game.homeTeamId,
            game.gameId,
            game.homeTeamLogo,
            game.awayTeamLogo,
        )
    }
    private fun favoriteGameToGame(favoriteGame: FavoriteGame): Game{
        return Game(
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