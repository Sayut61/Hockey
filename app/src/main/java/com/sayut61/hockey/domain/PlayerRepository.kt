package com.sayut61.hockey.domain

import com.sayut61.hockey.domain.entities.Player

interface PlayerRepository {
    suspend fun getPlayersFromApi(): List<Player>
    suspend fun addToFavoritePlayer(player: Player)
    suspend fun removeFromFavoritePlayer(player: Player)
    suspend fun getPlayersFromDB(): List<Player>
}