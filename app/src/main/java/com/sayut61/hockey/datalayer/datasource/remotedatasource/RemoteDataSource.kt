package com.sayut61.hockey.datalayer.datasource.remotedatasource

import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams.TeamInfoFromApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams.TeamsResponse
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teamslogos.DataResponse
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teamslogos.LogoFromApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import javax.inject.Inject



class RemoteDataSource @Inject constructor() {
    //Retrofit
    private interface RestNHLStatisticAPI {
        @GET(value = "v1/teams")
        suspend fun getAllTeams(): TeamsResponse
    }
    private interface RestNHLLogosAPI{
        @GET(value = "franchise?include=teams.logos")
        suspend fun getLogos(): DataResponse
    }

    private var retrofit = Retrofit.Builder()
        .baseUrl("https://statsapi.web.nhl.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private var retrofitForLogos = Retrofit.Builder()
        .baseUrl("https://records.nhl.com/site/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var service = retrofit.create(RestNHLStatisticAPI::class.java)
    private var serviceForLogos = retrofitForLogos.create(RestNHLLogosAPI::class.java)

    suspend fun getAllTeams(): List<TeamInfoFromApi> {
        return service.getAllTeams().teams
    }
        suspend fun getAllLogos(): List<LogoFromApi> {
            return serviceForLogos.getLogos().data.flatMap { it.teams.flatMap { it.logos } }
        }
    }



//    suspend fun getTeamLogo(teamId: Int): String?{
//        val listLogos = serviceForLogos.getLogos().data.flatMap { it.teams.flatMap { it.logos } }
//        val teamLogos = listLogos.filter { it.teamId == teamId }
//        val logo = teamLogos.maxByOrNull { it.endSeason }
//        return logo?.url
//    }
