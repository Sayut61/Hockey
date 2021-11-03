package com.sayut61.hockey.datalayer.repositories

import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSource
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams.TeamGeneralInfoFromSecondApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams.TeamInfoFromFirstApi
import com.sayut61.hockey.domain.entities.TeamGeneralInfo
import com.sayut61.hockey.domain.TeamRepository
import com.sayut61.hockey.domain.entities.TeamFullInfo
import com.sayut61.hockey.domain.entities.TeamPlayersInfo
import java.lang.Exception
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : TeamRepository {
    override suspend fun getTeamsInfo(): List<TeamGeneralInfo> {
        val teamsInfoFromFirstApi: List<TeamInfoFromFirstApi> = remoteDataSource.getTeamsFirstApi()
        val teamsGeneralInfoFromSecondApi: List<TeamGeneralInfoFromSecondApi> =
            remoteDataSource.getTeamsSecondApi()
        return teamsInfoFromFirstApi.map { team ->
            val teamInfoFromSecondApi = teamsGeneralInfoFromSecondApi.find { it.shortName == team.shortTeamName }
            TeamGeneralInfo(
                fullTeamName = team.fullTeamName,
                id = team.id,
                officialSiteUrl = team.officialSiteUrl,
                shortTeamName = team.shortTeamName,
                cityName = teamInfoFromSecondApi?.cityName,
                urlLogoTeam = teamInfoFromSecondApi?.wikipediaLogoUrl,
                stadiumID = teamInfoFromSecondApi?.stadiumID
            )
        }
    }

    override suspend fun getTeamFullInfo(teamId: Int): TeamFullInfo {
        val infoFromFirstApi = remoteDataSource.getTeamsFirstApi().find { it.id == teamId }
        val infoFromSecondApi = remoteDataSource.getTeamsSecondApi()
            .find { it.shortName == infoFromFirstApi?.shortTeamName }
        val fullInfoByTeam = remoteDataSource.getTeamFullInfo(teamId)
        return if(infoFromFirstApi != null && infoFromSecondApi != null) {
            val generalInfo = generalInfoToTeamGeneralInfo(infoFromFirstApi, infoFromSecondApi)
            TeamFullInfo(
                teamGeneralInfo =generalInfo,
                id = fullInfoByTeam.id,
                teamFullName = fullInfoByTeam.teamFullName,
                teamShortName = fullInfoByTeam.teamShortName,
                firstYearOfPlay = fullInfoByTeam.firstYearOfPlay,
                gamesPlayed = fullInfoByTeam.gamesPlayed,
                wins = fullInfoByTeam.wins,
                losses = fullInfoByTeam.losses,
                pts = fullInfoByTeam.pts,
                goalsPerGame = fullInfoByTeam.goalsPerGame,
                goalsAgainstPerGame = fullInfoByTeam.goalsAgainstPerGame,
                powerPlayPercentage = fullInfoByTeam.powerPlayPercentage,
                powerPlayGoals = fullInfoByTeam.powerPlayGoals,
                powerPlayGoalsAgainst = fullInfoByTeam.powerPlayGoalsAgainst,
                powerPlayOpportunities = fullInfoByTeam.powerPlayOpportunities,
                shotsPerGame = fullInfoByTeam.shotsPerGame,
                shotsAllowed = fullInfoByTeam.shotsPerGame,
                placeOnWins = fullInfoByTeam.placeOnWins,
                placeOnLosses = fullInfoByTeam.placeOnLosses,
                placeOnPts = fullInfoByTeam.placeOnPts,
                placeGoalsPerGame = fullInfoByTeam.placeGoalsPerGame,
                placeGoalsAgainstPerGame = fullInfoByTeam.placeGoalsAgainstPerGame
            )
        } else
            throw Exception("error get team info")
    }

    override suspend fun getPlayersInfo(teamId: Int): List<TeamPlayersInfo> {
        return remoteDataSource.getPlayersInfoByTeam(teamId).map {
            TeamPlayersInfo(
                jerseyNumber = it.jerseyNumber,
                playerId = it.playerId,
                fullName = it.fullName,
                linkOnFullInfoByPlayer = it.linkOnFullInfoByPlayer,
                type = it.type
            )
        }
    }


    fun generalInfoToTeamGeneralInfo(
    teamFirstApi: TeamInfoFromFirstApi,
    teamSecondApi: TeamGeneralInfoFromSecondApi
): TeamGeneralInfo {
    return TeamGeneralInfo(
        id = teamFirstApi.id,
        fullTeamName = teamFirstApi.fullTeamName,
        shortTeamName = teamFirstApi.shortTeamName,
        officialSiteUrl = teamFirstApi.officialSiteUrl,
        cityName = teamSecondApi.cityName,
        urlLogoTeam = teamSecondApi.wikipediaLogoUrl,
        stadiumID = teamSecondApi.stadiumID
    )
}
}
