package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.domain.PlayerRepository
import com.sayut61.hockey.domain.entities.Player
import javax.inject.Inject

class PlayersUseCases @Inject constructor(
    private val playerRepository: PlayerRepository
){
    suspend fun getPlayersListApi(): List<Player>{
        return playerRepository.getPlayersFromApi()
    }
    suspend fun getPlayersListDB(): List<Player>{
        return playerRepository.getPlayersFromDB()
    }
    suspend fun addToFavoritePlayer(id: Player){
        playerRepository.addToFavoritePlayer(id)
    }
    suspend fun removeFromFavoritePlayer(id: Player){
        playerRepository.removeFromFavoritePlayer(id)
    }
}