package com.sayut61.hockey.domain

import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import com.sayut61.hockey.domain.flow.LoadingResult
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface GameRepository {
    suspend fun getGamesFullInfo(date: LocalDate): Flow<List<GameFullInfo>>
    suspend fun getGameFullInfo(gameGeneralInfo: GameGeneralInfo): GameFullInfo
}