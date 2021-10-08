package com.sayut61.hockey.domain

import com.sayut61.hockey.domain.entities.Game

interface GamesFavRepositories {
    suspend fun getGames(): List<Game>
    suspend fun addToFavorite(game: Game)
    suspend fun removeFromFavorite(game: Game)
}