package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.domain.PlayersRepository
import com.sayut61.hockey.domain.entities.PlayerFullInfo
import com.sayut61.hockey.domain.entities.PlayerGeneralInfo
import com.sayut61.hockey.domain.entities.PlayerStatisticsInfo
import com.sayut61.hockey.domain.flow.LoadingResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlayersUseCases @Inject constructor(
    private val playersRepository: PlayersRepository
){
    fun getPlayersListApi(): Flow<LoadingResult<List<PlayerGeneralInfo>>> {
        return playersRepository.getPlayersFromApi()
    }
    fun getPlayersListDB(): Flow<LoadingResult<List<PlayerStatisticsInfo>>>{
        return playersRepository.getPlayersFromDB()
    }
    suspend fun getPlayerFullInfo(playerGeneralInfo: Int):PlayerFullInfo {
        return playersRepository.getPlayerFullInfo(playerGeneralInfo)
    }
    suspend fun addToFavoritePlayer(playerGeneralInfo: PlayerGeneralInfo){
        playersRepository.addToFavoritePlayer(playerGeneralInfo)
    }
    suspend fun removeFromFavoritePlayer(playerId: Int){
        playersRepository.removeFromFavoritePlayer(playerId)
    }

    suspend fun getPlayerStatistic(playerFullInfo: PlayerFullInfo): PlayerStatisticsInfo{
        return playersRepository.getPlayerStat(playerFullInfo)
    }
}