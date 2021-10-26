package com.sayut61.hockey.domain

import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import java.time.LocalDate

interface GameRepository {
    suspend fun getGamesGeneralInfo(date: LocalDate): List<GameGeneralInfo>
    suspend fun getGameFullInfo(gameGeneralInfo: GameGeneralInfo): GameFullInfo
}