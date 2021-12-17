package com.sayut61.hockey.domain

import com.sayut61.hockey.domain.entities.PlayerFullInfo
import com.sayut61.hockey.domain.entities.PlayerGeneralInfo
import com.sayut61.hockey.domain.entities.PlayerStatisticsInfo
import com.sayut61.hockey.domain.flow.LoadingResult
import kotlinx.coroutines.flow.Flow

interface PlayersRepository {
    fun getPlayersFromApi(): Flow<LoadingResult<List<PlayerGeneralInfo>>>
    fun getPlayersFromDB(): Flow<LoadingResult<List<PlayerStatisticsInfo>>>
    suspend fun getPlayerFullInfo(playerId: Int): PlayerFullInfo
    suspend fun addToFavoritePlayer(playerGeneralInfo: PlayerGeneralInfo)
    suspend fun removeFromFavoritePlayer(playerId: Int)
    suspend fun getPlayerStat(playerFullInfo: PlayerFullInfo): PlayerStatisticsInfo
}