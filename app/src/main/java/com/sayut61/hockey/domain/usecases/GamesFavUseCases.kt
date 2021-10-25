package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.domain.GamesFavRepositories
import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import javax.inject.Inject

class GamesFavUseCases @Inject constructor(
    private val getGamesFavListRepositories: GamesFavRepositories
) {
    suspend fun getFavoriteGames(): List<GameGeneralInfo>{
        return getGamesFavListRepositories.getFavoriteGames()
    }
    suspend fun removeFromFavoriteGame(gameGeneralInfo: GameGeneralInfo){
        getGamesFavListRepositories.removeFromFavoriteGame(gameGeneralInfo)
    }
    suspend fun addToFavoriteGame(gameGeneralInfo: GameGeneralInfo){
        getGamesFavListRepositories.addToFavoriteGame(gameGeneralInfo)
    }
}