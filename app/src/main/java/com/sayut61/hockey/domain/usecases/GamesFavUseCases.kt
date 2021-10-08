package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.domain.GamesFavRepositories
import com.sayut61.hockey.domain.entities.Game
import javax.inject.Inject

class GamesFavUseCases @Inject constructor(
    private val getGamesFavListRepositories: GamesFavRepositories
) {
    suspend fun getGames(): List<Game>{
        return getGamesFavListRepositories.getGames()
    }
    suspend fun removeFromFavorite(game: Game){
        getGamesFavListRepositories.removeFromFavorite(game)
    }

    suspend fun addToFavorite(game: Game){
        getGamesFavListRepositories.addToFavorite(game)
    }
}