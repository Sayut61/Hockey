package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.domain.PlayerRepository
import com.sayut61.hockey.domain.entities.PlayerFullInfo
import com.sayut61.hockey.domain.entities.PlayerGeneralInfo
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
    suspend fun addToFavoritePlayer(playerGeneralInfo: PlayerGeneralInfo){
        playerRepository.addToFavoritePlayer(playerGeneralInfo)
    }
    suspend fun removeFromFavoritePlayer(playerGeneralInfo: PlayerGeneralInfo){
        playerRepository.removeFromFavoritePlayer(playerGeneralInfo)
    }
    suspend fun getPlayerFullInfo(playerId: Int):PlayerFullInfo {
        return playerRepository.getPlayerFullInfo(playerId)
    }
}