package com.sayut61.hockey.datalayer.datasource.remotedatasource

import android.os.Build
import androidx.annotation.RequiresApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.games.GameFromFirstApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.games.GameFullInfoFromApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.players.PlayerFullInfoFromApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.players.PlayerGeneralInfoFromApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.players.PlayerPhoto
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.players.PlayerStatisticsFromFirstApiResponse
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.stadium.Stadium
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams.TeamFullInfoFromApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams.TeamGeneralInfoFromSecondApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams.TeamInfoFromFirstApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams.TeamPlayersFromApi
import java.time.LocalDate

interface RemoteDataSource {
    //Retrofit
    suspend fun getPlayerStatistic(playerId: Int): PlayerStatisticsFromFirstApiResponse

    suspend fun getPlayersPhoto(): List<PlayerPhoto>

    suspend fun getPlayerFullInfo(playerId: Int): PlayerFullInfoFromApi

    suspend fun getPlayersByTeam(teamId: Int): List<TeamPlayersFromApi>

    suspend fun getTeamFullInfo(teamId: Int): TeamFullInfoFromApi

    suspend fun getAllPlayers(): List<PlayerGeneralInfoFromApi>

    suspend fun getAllTeamsFromFirstApi(): List<TeamInfoFromFirstApi>

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getGamesByDate(date: LocalDate): List<GameFromFirstApi>

    suspend fun getGameDetails(link: String): GameFullInfoFromApi

    suspend fun getAllTeamsFromSecondApi(): List<TeamGeneralInfoFromSecondApi>

    suspend fun getStadiums(): List<Stadium>
}