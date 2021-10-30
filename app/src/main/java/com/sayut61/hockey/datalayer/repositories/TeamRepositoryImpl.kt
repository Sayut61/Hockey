package com.sayut61.hockey.datalayer.repositories

import com.sayut61.hockey.datalayer.datasource.loacaldatasource.TeamsInfoDao
import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSource
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams.TeamGeneralInfoFromSecondApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams.TeamInfoFromFirstApi
import com.sayut61.hockey.domain.entities.TeamGeneralInfo
import com.sayut61.hockey.domain.TeamRepository
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val teamsInfoDao: TeamsInfoDao
) : TeamRepository {
    override suspend fun getTeamsInfo(): List<TeamGeneralInfo> {
        val teamsInfoFromFirstApi: List<TeamInfoFromFirstApi> = remoteDataSource.getTeamsFirstApi()
        val teamsGeneralInfoFromSecondApi: List<TeamGeneralInfoFromSecondApi> = remoteDataSource.getTeamsSecondApi()

        return teamsInfoFromFirstApi.map { team->
            val isInDB = teamsInfoDao.getAllInfo().find { it.teamId == team.id } !=null
            val teamLogo = teamsGeneralInfoFromSecondApi.find { it.shortName == team.shortTeamName }
            TeamGeneralInfo(
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