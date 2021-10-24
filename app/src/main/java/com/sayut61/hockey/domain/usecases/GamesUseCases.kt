package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.domain.GamesRepositories
import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import java.time.LocalDate
import javax.inject.Inject

class GamesUseCases @Inject constructor(
    private val gamesRepositories: GamesRepositories
) {
    suspend fun getGamesInfo(date: LocalDate): List<GameGeneralInfo>{
        return gamesRepositories.getGamesGeneralInfo(date)
    }
    suspend fun getGameFullInfo(gameGeneralInfo: GameGeneralInfo): GameFullInfo{
        return gamesRepositories.getGameFullInfo(gameGeneralInfo)
    }
//    suspend fun getGameFullInfo(date: LocalDate): GameFullInfo{
//        return gamesRepositories.getGameFullInfo(gameGeneralInfo)
//    }
}