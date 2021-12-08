package com.sayut61.hockey.domain

import com.sayut61.hockey.domain.entities.PlayerFullInfo
import com.sayut61.hockey.domain.entities.PlayerGeneralInfo
import com.sayut61.hockey.domain.entities.PlayerStatisticsInfo
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {
    fun getPlayersFromApi(): Flow<List<PlayerGeneralInfo>>
    suspend fun getPlayersFromDB(): List<PlayerStatisticsInfo>
    suspend fun getPlayerFullInfo(playerId: Int): PlayerFullInfo
    suspend fun addToFavoritePlayer(playerGeneralInfo: PlayerGeneralInfo)
    suspend fun removeFromFavoritePlayer(playerId: Int)
    suspend fun getPlayerStat(playerFullInfo: PlayerFullInfo): PlayerStatisticsInfo
}