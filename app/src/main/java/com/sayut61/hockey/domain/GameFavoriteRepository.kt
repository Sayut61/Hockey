package com.sayut61.hockey.domain

import com.sayut61.hockey.domain.entities.GameGeneralInfo

interface GameFavoriteRepository {
    suspend fun getFavoriteGames(): List<GameGeneralInfo>
    suspend fun addToFavoriteGame(gameGeneralInfo: GameGeneralInfo)
    suspend fun removeFromFavoriteGame(gameGeneralInfo: GameGeneralInfo)
}