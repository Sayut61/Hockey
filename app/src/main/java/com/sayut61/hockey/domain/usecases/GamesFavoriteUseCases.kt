package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.domain.GamesFavoriteRepository
import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import com.sayut61.hockey.domain.flow.LoadingResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GamesFavoriteUseCases @Inject constructor(
    private val getGamesFavoriteRepository: GamesFavoriteRepository
) {
    fun getFavoriteGames(): Flow<LoadingResult<List<GameFullInfo>>>{
        return getGamesFavoriteRepository.getFavoriteGames()
    }
    suspend fun removeFromFavoriteGame(gameGeneralInfo: GameGeneralInfo){
        getGamesFavoriteRepository.removeFromFavoriteGame(gameGeneralInfo)
    }
    suspend fun addToFavoriteGame(gameGeneralInfo: GameGeneralInfo){
        getGamesFavoriteRepository.addToFavoriteGame(gameGeneralInfo)
    }
}