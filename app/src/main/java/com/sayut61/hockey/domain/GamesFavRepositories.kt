package com.sayut61.hockey.domain

import com.sayut61.hockey.domain.entities.Game

interface GamesFavRepositories {
    suspend fun getFavoriteGames(): List<Game>
    suspend fun addToFavoriteGame(game: Game)
    suspend fun removeFromFavoriteGame(game: Game)
}