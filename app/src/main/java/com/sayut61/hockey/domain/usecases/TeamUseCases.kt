package com.sayut61.hockey.domain.usecases

import com.sayut61.hockey.domain.TeamRepositories
import com.sayut61.hockey.domain.entities.Team
import javax.inject.Inject

class TeamUseCases @Inject constructor(
    private val teamRepositories: TeamRepositories
) {
    suspend fun getTeamsInfo(): List<Team>{
        return teamRepositories.getTeamsInfo()
    }
}