package com.sayut61.hockey.domain

import com.sayut61.hockey.domain.entities.TeamFullInfo
import com.sayut61.hockey.domain.entities.TeamGeneralInfo
import com.sayut61.hockey.domain.entities.TeamPlayersInfo

interface TeamsRepository {
    suspend fun getTeamsInfo(): List<TeamGeneralInfo>
    suspend fun getTeamFullInfo(teamId: Int): TeamFullInfo
    suspend fun getPlayersInfo(teamId: Int): List<TeamPlayersInfo>
}