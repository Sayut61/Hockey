package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.domain.PlayerRepository
import com.sayut61.hockey.domain.entities.PlayerFullInfo
import com.sayut61.hockey.domain.entities.PlayerGeneralInfo
import com.sayut61.hockey.domain.entities.PlayerStatisticsInfo
import com.sayut61.hockey.domain.flow.LoadingResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlayersUseCases @Inject constructor(
    private val playerRepository: PlayerRepository
){
    fun getPlayersListApi(): Flow<LoadingResult<List<PlayerGeneralInfo>>> {
        return playerRepository.getPlayersFromApi()
    }
    suspend fun getPlayersListDB(): List<PlayerStatisticsInfo>{
        return playerRepository.getPlayersFromDB()
    }
    suspend fun getPlayerFullInfo(playerGeneralInfo: Int):PlayerFullInfo {
        return playerRepository.getPlayerFullInfo(playerGeneralInfo)
    }
    suspend fun addToFavoritePlayer(playerGeneralInfo: PlayerGeneralInfo){
        playerRepository.addToFavoritePlayer(playerGeneralInfo)
    }
    suspend fun removeFromFavoritePlayer(playerId: Int){
        playerRepository.removeFromFavoritePlayer(playerId)
    }

    suspend fun getPlayerStatistic(playerFullInfo: PlayerFullInfo): PlayerStatisticsInfo{
        return playerRepository.getPlayerStat(playerFullInfo)
    }
}