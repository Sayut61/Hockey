package com.sayut61.hockey.datalayer.repositories

import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSource
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams.TeamInfoFromApi
import com.sayut61.hockey.domain.entities.Team
import com.sayut61.hockey.domain.TeamRepositories
import javax.inject.Inject

class TeamRepositoriesImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): TeamRepositories {
    override suspend fun getTeamsInfo(): List<Team> {
        val allInfoFromTeamInfoApi: List<TeamInfoFromApi> = remoteDataSource.getAllTeams()
           return allInfoFromTeamInfoApi.map {
               Team(
                   name = it.name,
                   id = it.id,
                   firstYearOfPlay = it.firstYearOfPlay,
                   officialSiteUrl = it.officialSiteUrl,
                   teamName = it.teamName,
               )
           }
    }
}