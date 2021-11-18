package com.sayut61.hockey.domain

import com.sayut61.hockey.domain.entities.PlayerFullInfo
import com.sayut61.hockey.domain.entities.PlayerGeneralInfo
import com.sayut61.hockey.domain.entities.PlayerStatisticsInfo

interface PlayerRepository {
    suspend fun getPlayersFromApi(): List<PlayerGeneralInfo>
    suspend fun getPlayersFromDB(): List<PlayerGeneralInfo>
    suspend fun getPlayerFullInfo(playerId: Int): PlayerFullInfo
    suspend fun getPlayerStatistics(playerId: Int): List<PlayerStatisticsInfo>
    suspend fun addToFavoritePlayer(playerGeneralInfo: PlayerGeneralInfo)
    suspend fun removeFromFavoritePlayer(playerGeneralInfo: PlayerGeneralInfo)
}