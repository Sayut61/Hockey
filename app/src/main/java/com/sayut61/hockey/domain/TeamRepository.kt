package com.sayut61.hockey.domain

import com.sayut61.hockey.domain.entities.TeamGeneralInfo

interface TeamRepository {
    suspend fun getTeamsInfo(): List<TeamGeneralInfo>
}