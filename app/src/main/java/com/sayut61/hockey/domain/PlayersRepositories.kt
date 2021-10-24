package com.sayut61.hockey.domain

import com.sayut61.hockey.domain.entities.Players

interface PlayersRepositories {
    suspend fun getPlayersFromApi(): List<Players>
    suspend fun addToFavoritePlayer(id: Players)
    suspend fun removeFromFavoritePlayer(id: Players)
    suspend fun getPlayersFromDB(): List<Players>
}