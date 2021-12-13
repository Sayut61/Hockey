package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.domain.GameFavoriteRepository
import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import com.sayut61.hockey.domain.flow.LoadingResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GamesFavUseCases @Inject constructor(
    private val getGameFavoriteRepository: GameFavoriteRepository
) {
    fun getFavoriteGames(): Flow<LoadingResult<List<GameFullInfo>>>{
        return getGameFavoriteRepository.getFavoriteGames()
    }
    suspend fun removeFromFavoriteGame(gameGeneralInfo: GameGeneralInfo){
        getGameFavoriteRepository.removeFromFavoriteGame(gameGeneralInfo)
    }
    suspend fun addToFavoriteGame(gameGeneralInfo: GameGeneralInfo){
        getGameFavoriteRepository.addToFavoriteGame(gameGeneralInfo)
    }
}