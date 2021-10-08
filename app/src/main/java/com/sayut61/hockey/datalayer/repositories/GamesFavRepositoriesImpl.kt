package com.sayut61.hockey.datalayer.repositories

import com.sayut61.hockey.datalayer.datasource.loacaldatasource.GamesInfoDao
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.dto.FavoriteGame
import com.sayut61.hockey.domain.GamesFavRepositories
import com.sayut61.hockey.domain.entities.Game
import javax.inject.Inject

class GamesFavRepositoriesImpl @Inject constructor(
    val gamesInfoDao: GamesInfoDao
): GamesFavRepositories {


    override suspend fun getGames(): List<Game> {
        val listFavoriteGames = gamesInfoDao.getAllInfo()
        return listFavoriteGames.map {
            favoriteGameToGame(it)
        }
    }

    override suspend fun removeFromFavorite(game: Game) {
        gamesInfoDao.delete(gameToFavoriteGame(game))
    }

    override suspend fun addToFavorite(game: Game) {
        gamesInfoDao.insert(gameToFavoriteGame(game))
    }



    private fun gameToFavoriteGame(game: Game): FavoriteGame{
        return FavoriteGame(
            game.gameDate,
            game.linkOnDetailInfoByGame,
            game.awayTeamName,
            game.awayTeamId,
            game.homeTeamName,
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
            favoriteGame.awayTeamName,
            favoriteGame.awayTeamId,
            favoriteGame.homeTeamName,
            favoriteGame.homeTeamId,
            favoriteGame.gameId,
            favoriteGame.homeTeamLogo,
            favoriteGame.awayTeamLogo,
            true
        )
    }
}