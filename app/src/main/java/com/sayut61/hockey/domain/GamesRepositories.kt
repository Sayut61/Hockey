package com.sayut61.hockey.domain

import com.sayut61.hockey.domain.entities.Game
import java.time.LocalDate

interface GamesRepositories {
    suspend fun getGameInfo(date: LocalDate): List<Game>
}