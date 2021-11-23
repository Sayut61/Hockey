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
}