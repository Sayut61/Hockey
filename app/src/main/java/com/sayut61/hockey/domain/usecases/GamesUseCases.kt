package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.domain.GameRepository
import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import java.time.LocalDate
import javax.inject.Inject

class GamesUseCases @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend fun getGamesInfo(date: LocalDate): List<GameGeneralInfo>{
        return gameRepository.getGamesGeneralInfo(date)
    }
    suspend fun getGameFullInfo(gameGeneralInfo: GameGeneralInfo): GameFullInfo{
        return gameRepository.getGameFullInfo(gameGeneralInfo)
    }
//    suspend fun getGameFullInfo(date: LocalDate): GameFullInfo{
//        return gamesRepositories.getGameFullInfo(gameGeneralInfo)
//    }
}