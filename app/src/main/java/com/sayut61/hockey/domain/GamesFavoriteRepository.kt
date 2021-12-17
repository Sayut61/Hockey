package com.sayut61.hockey.domain

import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import com.sayut61.hockey.domain.flow.LoadingResult
import kotlinx.coroutines.flow.Flow

interface GamesFavoriteRepository {
    fun getFavoriteGames(): Flow<LoadingResult<List<GameFullInfo>>>
    suspend fun addToFavoriteGame(gameGeneralInfo: GameGeneralInfo)
    suspend fun removeFromFavoriteGame(gameGeneralInfo: GameGeneralInfo)
}