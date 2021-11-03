package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.domain.TeamRepository
import com.sayut61.hockey.domain.entities.TeamFullInfo
import com.sayut61.hockey.domain.entities.TeamGeneralInfo
import com.sayut61.hockey.domain.entities.TeamPlayersInfo
import javax.inject.Inject

class TeamUseCases @Inject constructor(
    private val teamRepository: TeamRepository
) {
    suspend fun getTeamsInfo(): List<TeamGeneralInfo>{
        return teamRepository.getTeamsInfo()
    }
    suspend fun getTeamFullInfo(teamId: Int): TeamFullInfo {
        return teamRepository.getTeamFullInfo(teamId)
    }
    suspend fun getPlayersInfo(teamId: Int): List<TeamPlayersInfo>{
        return teamRepository.getPlayersInfo(teamId)
    }
}