package com.sayut61.hockey.domain

import com.sayut61.hockey.domain.entities.Team

interface TeamRepository {
    suspend fun getTeamsInfo(): List<Team>
}