package com.sayut61.hockey.datalayer.repositories

import com.sayut61.hockey.datalayer.datasource.loacaldatasource.TeamsInfoDao
import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSource
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.TeamsInfo.TeamsInfoFromSecondAPI
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams.TeamInfoFromApi
import com.sayut61.hockey.domain.entities.Team
import com.sayut61.hockey.domain.TeamRepositories
import javax.inject.Inject

class TeamRepositoriesImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val teamsInfoDao: TeamsInfoDao
) : TeamRepositories {
    override suspend fun getTeamsInfo(): List<Team> {
        val allInfoFromTeamInfoApi: List<TeamInfoFromApi> = remoteDataSource.getAllTeams()
        val logoFromSecondApi: List<TeamsInfoFromSecondAPI> = remoteDataSource.getAllTeamsInfo()

        return allInfoFromTeamInfoApi.map {team->
            val isInDB = teamsInfoDao.getAllInfo().find { it.teamId == team.id } !=null
            val teamLogo = logoFromSecondApi.find { it.Name == team.teamName }
            Team(
                name = team.name,
                id = team.id,
                officialSiteUrl = team.officialSiteUrl,
                teamName = team.teamName,
                isInFavorite = isInDB,
                urlLogoTeam = teamLogo?.WikipediaLogoUrl,
                StadiumID = teamLogo!!.StadiumID
            )
        }
    }
}