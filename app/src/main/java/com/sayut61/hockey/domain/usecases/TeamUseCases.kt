package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams.FullInfoByTeam
import com.sayut61.hockey.domain.TeamRepository
import com.sayut61.hockey.domain.entities.TeamFullInfo
import com.sayut61.hockey.domain.entities.TeamGeneralInfo
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
}