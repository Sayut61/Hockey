package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.domain.GamesRepository
import com.sayut61.hockey.domain.entities.GameFullInfo
import com.sayut61.hockey.domain.entities.GameGeneralInfo
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GamesUseCases @Inject constructor(
    private val gamesRepository: GamesRepository
) {
   suspend fun getGamesInfo(date: LocalDate): Flow<List<GameFullInfo>>{
        return gamesRepository.getGamesFullInfo(date)
    }
    suspend fun getGameFullInfo(gameGeneralInfo: GameGeneralInfo): GameFullInfo{
        return gamesRepository.getGameFullInfo(gameGeneralInfo)
    }
}