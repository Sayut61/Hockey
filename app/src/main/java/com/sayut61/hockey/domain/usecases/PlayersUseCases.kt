package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.domain.PlayerRepository
import com.sayut61.hockey.domain.entities.PlayerFullInfo
import com.sayut61.hockey.domain.entities.PlayerGeneralInfo
import com.sayut61.hockey.domain.entities.PlayerStatisticsInfo
import javax.inject.Inject

class PlayersUseCases @Inject constructor(
    private val playerRepository: PlayerRepository
){
    suspend fun getPlayersListApi(): List<PlayerGeneralInfo>{
        return playerRepository.getPlayersFromApi()
    }
    suspend fun getPlayersListDB(): List<PlayerGeneralInfo>{
        return playerRepository.getPlayersFromDB()
    }
    suspend fun getPlayerFullInfo(playerGeneralInfo: Int):PlayerFullInfo {
        return playerRepository.getPlayerFullInfo(playerGeneralInfo)
    }
    suspend fun getPlayerStatisticInfo(playerGeneralInfo: Int): List<PlayerStatisticsInfo> {
        return playerRepository.getPlayerStatistics(playerGeneralInfo)
    }
    suspend fun addToFavoritePlayer(playerGeneralInfo: PlayerGeneralInfo){
        playerRepository.addToFavoritePlayer(playerGeneralInfo)
    }
    suspend fun removeFromFavoritePlayer(playerGeneralInfo: PlayerGeneralInfo){
        playerRepository.removeFromFavoritePlayer(playerGeneralInfo)
    }
}