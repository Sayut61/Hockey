package com.sayut61.hockey.datalayer.repositories

import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSource
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams.TeamInfoFromApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teamslogos.LogoFromApi
import com.sayut61.hockey.domain.entities.Team
import com.sayut61.hockey.domain.TeamRepositories
import javax.inject.Inject

class TeamRepositoriesImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): TeamRepositories {
    override suspend fun getTeamsInfo(): List<Team> {
        val allLogos: List<LogoFromApi> = remoteDataSource.getAllLogos()
        val allInfoFromTeamInfoApi: List<TeamInfoFromApi> = remoteDataSource.getAllTeams()
           return allInfoFromTeamInfoApi.map {team->
               val teamLogos = allLogos.filter { it.teamId == team.id }
               val lastLogo = teamLogos.maxByOrNull{ it.endSeason }
               Team(
                   name = team.name,
                   id = team.id,
                   firstYearOfPlay = team.firstYearOfPlay,
                   officialSiteUrl = team.officialSiteUrl,
                   teamName = team.teamName,
                   logo = lastLogo?.url
               )
           }
    }
}