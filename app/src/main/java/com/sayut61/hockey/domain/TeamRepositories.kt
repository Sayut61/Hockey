package com.sayut61.hockey.domain

import com.sayut61.hockey.domain.entities.Team

interface TeamRepositories {
    suspend fun getTeamsInfo(): List<Team>
}