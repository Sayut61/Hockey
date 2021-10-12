package com.sayut61.hockey.datalayer.repositories

import com.sayut61.hockey.datalayer.datasource.loacaldatasource.TeamsInfoDao
import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSource
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.TeamsInfo.TeamInfoFromSecondApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams.TeamInfoFromFirstApi
import com.sayut61.hockey.domain.entities.Team
import com.sayut61.hockey.domain.TeamRepositories
import javax.inject.Inject

class TeamRepositoriesImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val teamsInfoDao: TeamsInfoDao
) : TeamRepositories {
    override suspend fun getTeamsInfo(): List<Team> {
        val teamsInfoFromFirstApi: List<TeamInfoFromFirstApi> = remoteDataSource.getTeamsFirstApi()
        val teamsInfoFromSecondApi: List<TeamInfoFromSecondApi> = remoteDataSource.getTeamsSecondApi()

        return teamsInfoFromFirstApi.map { team->
            val isInDB = teamsInfoDao.getAllInfo().find { it.teamId == team.id } !=null
            val teamLogo = teamsInfoFromSecondApi.find { it.shortName == team.shortTeamName }
            Team(
                fullTeamName = team.fullTeamName,
                id = team.id,
                officialSiteUrl = team.officialSiteUrl,
                shortTeamName = team.shortTeamName,
                cityName = teamLogo?.cityName,
                isInFavoriteTeam = isInDB,
                urlLogoTeam = teamLogo?.wikipediaLogoUrl,
                StadiumID = teamLogo!!.stadiumID
            )
        }
    }
}