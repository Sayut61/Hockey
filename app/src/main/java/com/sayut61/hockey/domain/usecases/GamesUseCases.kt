package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.domain.GamesRepositories
import com.sayut61.hockey.domain.entities.Game
import java.time.LocalDate
import javax.inject.Inject

class GamesUseCases @Inject constructor(
    private val gamesRepositories: GamesRepositories
) {
    suspend fun getGamesInfo(date: LocalDate): List<Game>{
        return gamesRepositories.getGameInfo(date)
    }
}