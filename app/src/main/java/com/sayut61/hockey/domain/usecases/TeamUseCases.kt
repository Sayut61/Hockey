package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.domain.TeamRepository
import com.sayut61.hockey.domain.entities.Team
import javax.inject.Inject

class TeamUseCases @Inject constructor(
    private val teamRepository: TeamRepository
) {
    suspend fun getTeamsInfo(): List<Team>{
        return teamRepository.getTeamsInfo()
    }
}