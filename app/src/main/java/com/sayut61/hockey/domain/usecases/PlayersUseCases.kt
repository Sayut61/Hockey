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
    suspend fun addToFavoritePlayer(player: Player){
        playerRepository.addToFavoritePlayer(player)
    }
    suspend fun removeFromFavoritePlayer(player: Player){
        playerRepository.removeFromFavoritePlayer(player)
    }
}