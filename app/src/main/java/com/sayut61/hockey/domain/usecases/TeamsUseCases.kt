package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.domain.TeamsRepository
import com.sayut61.hockey.domain.entities.TeamFullInfo
import com.sayut61.hockey.domain.entities.TeamGeneralInfo
import com.sayut61.hockey.domain.entities.TeamPlayersInfo
import javax.inject.Inject

class TeamsUseCases @Inject constructor(
    private val teamsRepository: TeamsRepository
) {
    suspend fun getTeamsInfo(): List<TeamGeneralInfo>{
        return teamsRepository.getTeamsInfo()
    }
    suspend fun getTeamFullInfo(teamId: Int): TeamFullInfo {
        return teamsRepository.getTeamFullInfo(teamId)
    }
    suspend fun getPlayersInfo(teamId: Int): List<TeamPlayersInfo>{
        return teamsRepository.getPlayersInfo(teamId)
    }
}