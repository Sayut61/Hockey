package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.domain.PlayersRepositories
import com.sayut61.hockey.domain.entities.Players
import javax.inject.Inject

class PlayersUseCases @Inject constructor(
    private val playersRepositories: PlayersRepositories
){
    suspend fun getPlayersListApi(): List<Players>{
        return playersRepositories.getPlayersFromApi()
    }
    suspend fun getPlayersListDB(): List<Players>{
        return playersRepositories.getPlayersFromDB()
    }
    suspend fun addToFavoritePlayer(id: Players){
        playersRepositories.addToFavoritePlayer(id)
    }
    suspend fun removeFromFavoritePlayer(id: Players){
        playersRepositories.removeFromFavoritePlayer(id)
    }
}