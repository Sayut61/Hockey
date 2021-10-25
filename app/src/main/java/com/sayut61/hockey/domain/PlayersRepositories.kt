package com.sayut61.hockey.domain

import com.sayut61.hockey.domain.entities.Player

interface PlayersRepositories {
    suspend fun getPlayersFromApi(): List<Player>
    suspend fun addToFavoritePlayer(id: Player)
    suspend fun removeFromFavoritePlayer(id: Player)
    suspend fun getPlayersFromDB(): List<Player>
}