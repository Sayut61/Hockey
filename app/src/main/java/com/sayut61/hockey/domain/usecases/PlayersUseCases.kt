package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.domain.PlayersRepositories
import com.sayut61.hockey.domain.entities.Player
import javax.inject.Inject

class PlayersUseCases @Inject constructor(
    private val playersRepositories: PlayersRepositories
){
    suspend fun getPlayersListApi(): List<Player>{
        return playersRepositories.getPlayersFromApi()
    }
    suspend fun getPlayersListDB(): List<Player>{
        return playersRepositories.getPlayersFromDB()
    }
    suspend fun addToFavoritePlayer(id: Player){
        playersRepositories.addToFavoritePlayer(id)
    }
    suspend fun removeFromFavoritePlayer(id: Player){
        playersRepositories.removeFromFavoritePlayer(id)
    }
}