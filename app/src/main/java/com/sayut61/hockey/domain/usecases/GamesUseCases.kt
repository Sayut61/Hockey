package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.domain.GameRepository
import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import com.sayut61.hockey.domain.flow.LoadingResult
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GamesUseCases @Inject constructor(
    private val gameRepository: GameRepository
) {
   suspend fun getGamesInfo(date: LocalDate): Flow<List<GameFullInfo>>{
        return gameRepository.getGamesFullInfo(date)
    }
    suspend fun getGameFullInfo(gameGeneralInfo: GameGeneralInfo): GameFullInfo{
        return gameRepository.getGameFullInfo(gameGeneralInfo)
    }
}