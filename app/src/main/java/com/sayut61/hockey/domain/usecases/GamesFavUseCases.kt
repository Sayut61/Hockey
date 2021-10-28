package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.domain.GameFavoriteRepository
import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import javax.inject.Inject

class GamesFavUseCases @Inject constructor(
    private val getGameFavoriteRepository: GameFavoriteRepository
) {
    suspend fun getFavoriteGames(): List<GameFullInfo>{
        return getGameFavoriteRepository.getFavoriteGames()
    }
    suspend fun removeFromFavoriteGame(gameGeneralInfo: GameGeneralInfo){
        getGameFavoriteRepository.removeFromFavoriteGame(gameGeneralInfo)
    }
    suspend fun addToFavoriteGame(gameGeneralInfo: GameGeneralInfo){
        getGameFavoriteRepository.addToFavoriteGame(gameGeneralInfo)
    }
}