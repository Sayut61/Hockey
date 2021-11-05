package com.sayut61.hockey.domain

import com.sayut61.hockey.domain.entities.PlayerFullInfo
import com.sayut61.hockey.domain.entities.PlayerGeneralInfo

interface PlayerRepository {
    suspend fun getPlayersFromApi(): List<PlayerGeneralInfo>
    suspend fun addToFavoritePlayer(playerGeneralInfo: PlayerGeneralInfo)
    suspend fun removeFromFavoritePlayer(playerGeneralInfo: PlayerGeneralInfo)
    suspend fun getPlayersFromDB(): List<PlayerGeneralInfo>
    suspend fun getPlayerFullInfo(playerId: Int): PlayerFullInfo
}