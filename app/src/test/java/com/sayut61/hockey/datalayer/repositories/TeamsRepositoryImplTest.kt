package com.sayut61.hockey.datalayer.repositories

import com.google.gson.annotations.SerializedName
import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSource
import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSourceImpl
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.games.GameFromFirstApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.games.GameFullInfoFromApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.players.PlayerFullInfoFromApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.players.PlayerGeneralInfoFromApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.players.PlayerPhoto
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.players.PlayerStatisticsFromFirstApiResponse
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.stadium.Stadium
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams.*
import com.sayut61.hockey.domain.entities.TeamGeneralInfo
import com.sayut61.hockey.domain.entities.TeamPlayersInfo
import kotlinx.coroutines.runBlocking
import org.junit.Assert

import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import org.junit.Assert.*

class TeamsRepositoryImplTest {
    lateinit var teamsRepositoryImpl: TeamsRepositoryImpl
    val remoteDataSource =  FakeDataSource()

    @Before
    fun setUp() {
        teamsRepositoryImpl = TeamsRepositoryImpl(remoteDataSource)
    }

    @Test
    fun generalInfoToTeamGeneralInfo() {
        val firstApiInfo = TeamInfoFromFirstApi(
            id = 1,
            fullTeamName = "Dallas Stars",
            shortTeamName = "Stars",
            officialSiteUrl = "www.nhl.ru",
            venue = Venue("Stadium")
        )
        val secondApiInfo = TeamGeneralInfoFromSecondApi(
            shortName = "Stars",
            cityName = "Dallas",
            wikipediaLogoUrl = "www.logo.ru",
            stadiumID = 10
        )
        val result = teamsRepositoryImpl.generalInfoToTeamGeneralInfo(
            teamFirstApi = firstApiInfo,
            teamSecondApi = secondApiInfo
        )
        val generalInfo = TeamGeneralInfo(
            id = 1,
            fullTeamName = "Dallas Stars",
            shortTeamName = "Stars",
            officialSiteUrl = "www.nhl.ru",
            cityName = "Dallas",
            stadiumID = 10,
            urlLogoTeam = "www.logo.ru"
        )
        assertEquals(generalInfo, result)
    }

    @Test
    fun getPlayersInfo() = runBlocking {
        val teamId = 1
        val listPlayer = teamsRepositoryImpl.getPlayersInfo(teamId)
        val listPlayersInfo = listOf(
            TeamPlayersInfo(
                jerseyNumber = 1,
                playerId = 1,
                fullName = "Alex",
                linkOnFullInfoByPlayer = "link1",
                type = "type1"
            ),
            TeamPlayersInfo(
            jerseyNumber = 2,
            playerId = 2,
            fullName = "Igor",
            linkOnFullInfoByPlayer = "link2",
            type = "type2"
        ))
        assertEquals(listPlayer, listPlayersInfo)
    }


}

class FakeDataSource() : RemoteDataSource {

    override suspend fun getPlayerStatistic(playerId: Int): PlayerStatisticsFromFirstApiResponse {
        TODO("Not yet implemented")
        /*return when(playerId){
            1->{}
            2->{}
        }*/
    }

    override suspend fun getPlayersPhoto(): List<PlayerPhoto> {
        TODO("Not yet implemented")
    }

    override suspend fun getPlayerFullInfo(playerId: Int): PlayerFullInfoFromApi {
        TODO("Not yet implemented")
    }

    override suspend fun getPlayersByTeam(teamId: Int): List<TeamPlayersFromApi> {
        return listOf(
            TeamPlayersFromApi(
                jerseyNumber = 1,
                playerId = 1,
                fullName = "Alex",
                linkOnFullInfoByPlayer = "link1",
                type = "type1"
            ),
            TeamPlayersFromApi(
                jerseyNumber = 2,
                playerId = 2,
                fullName = "Igor",
                linkOnFullInfoByPlayer = "link2",
                type = "type2"
            )
        )
    }

    override suspend fun getTeamFullInfo(teamId: Int): TeamFullInfoFromApi {
        TODO("Not yet implemented")
    }

    override suspend fun getAllPlayers(): List<PlayerGeneralInfoFromApi> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllTeamsFromFirstApi(): List<TeamInfoFromFirstApi> {
        return listOf(
            TeamInfoFromFirstApi(
                id = 1,
                fullTeamName = "Dallas Stars",
                shortTeamName = "Stars",
                officialSiteUrl = "www.nhl.ru",
                venue = Venue("Stadium")
            )
        )
    }

    override suspend fun getGamesByDate(date: LocalDate): List<GameFromFirstApi> {
        TODO("Not yet implemented")
    }

    override suspend fun getGameDetails(link: String): GameFullInfoFromApi {
        TODO("Not yet implemented")
    }

    override suspend fun getAllTeamsFromSecondApi(): List<TeamGeneralInfoFromSecondApi> {
        return listOf(
            TeamGeneralInfoFromSecondApi(
                shortName = "Stars",
                cityName = "Dallas",
                wikipediaLogoUrl = "www.logo.ru",
                stadiumID = 10
            )
        )
    }

    override suspend fun getStadiums(): List<Stadium> {
        TODO("Not yet implemented")
    }
}
