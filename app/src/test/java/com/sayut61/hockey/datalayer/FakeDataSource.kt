package com.sayut61.hockey.datalayer

import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSource
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.games.GameFromFirstApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.games.GameFullInfoFromApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.players.PlayerFullInfoFromApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.players.PlayerGeneralInfoFromApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.players.PlayerPhoto
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.players.PlayerStatisticsFromFirstApiResponse
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.stadium.Stadium
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams.*
import java.time.LocalDate

class FakeDataSource() : RemoteDataSource {

    override suspend fun getPlayerStatistic(playerId: Int): PlayerStatisticsFromFirstApiResponse {
        TODO("Not yet implemented")
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
        return TeamFullInfoFromApi(
            id = 1,
            teamFullName = "Dallas Stars",
            teamShortName = "Stars",
            firstYearOfPlay = 1900,
            gamesPlayed = 10,
            wins = 10,
            losses = 0,
            pts = 20,
            goalsPerGame = 20.0,
            goalsAgainstPerGame = 10.0,
            powerPlayPercentage = "10",
            powerPlayGoals = 20.0,
            powerPlayGoalsAgainst = 20.0,
            powerPlayOpportunities = 20.0,
            shotsPerGame = 10,
            shotsAllowed = 10,
            placeOnWins = "20",
            placeOnLosses = "20",
            placeOnPts = "20",
            placeGoalsPerGame = "20",
            placeGoalsAgainstPerGame = "20"
        )
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