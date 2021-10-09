package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.domain.GamesFavRepositories
import com.sayut61.hockey.domain.entities.Game
import javax.inject.Inject

class GamesFavUseCases @Inject constructor(
    private val getGamesFavListRepositories: GamesFavRepositories
) {
    suspend fun getFavoriteGames(): List<Game>{
        return getGamesFavListRepositories.getFavoriteGames()
    }
    suspend fun removeFromFavoriteGame(game: Game){
        getGamesFavListRepositories.removeFromFavoriteGame(game)
    }
    suspend fun addToFavoriteGame(game: Game){
        getGamesFavListRepositories.addToFavoriteGame(game)
    }
}