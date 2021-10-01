package com.sayut61.hockey.datalayer.datasource.remotedatasource

import android.os.Build
import androidx.annotation.RequiresApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.TeamsInfo.TeamsInfoFromSecondAPI
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.calendar.CalendarResponse
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.calendar.InfoByGame
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.stadium.StadiumInfo
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams.TeamInfoFromApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams.TeamsResponse
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teamslogos.DataResponse
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teamslogos.LogoFromApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate
import javax.inject.Inject



class RemoteDataSource @Inject constructor() {

    //Retrofit

    private interface RestNHLInfoAPI {
        @GET(value = "v1/teams")
        suspend fun getAllTeams(): TeamsResponse

        @GET(value = "v1/schedule")
        suspend fun getInfoByGameDay(@Query("date") date: String): CalendarResponse
    }
    private interface RestNHLLogosAPI{
        @GET(value = "franchise?include=teams.logos")
        suspend fun getLogos(): DataResponse
    }
    private interface RestNHLInfoSecondAPI{
        @GET(value = "teams?key=1dd2b753fe264d2b9d7d08d0988b34e2")
        suspend fun getTeamsInfoBySecondAPI(): List<TeamsInfoFromSecondAPI>

        @GET(value = "Stadiums?key=1dd2b753fe264d2b9d7d08d0988b34e2")
        suspend fun getStadiumInfoBySecondAPI(): List<StadiumInfo>
    }

    //Retrofit

    private var retrofitForInfo = Retrofit.Builder()
        .baseUrl("https://statsapi.web.nhl.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private var retrofitForLogos = Retrofit.Builder()
        .baseUrl("https://records.nhl.com/site/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private var retrofitForTeamsAPI = Retrofit.Builder()
        .baseUrl("https://api.sportsdata.io/v3/nhl/scores/json/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //Retrofit

    private var serviceForInfo = retrofitForInfo.create(RestNHLInfoAPI::class.java)
    private var serviceForLogos = retrofitForLogos.create(RestNHLLogosAPI::class.java)
    private var serviceForTeamsAPI = retrofitForTeamsAPI.create(RestNHLInfoSecondAPI::class.java)

    //Retrofit

    suspend fun getAllTeams(): List<TeamInfoFromApi> {
        return serviceForInfo.getAllTeams().teams
    }
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getInfoByGameDay(date: LocalDate): List<InfoByGame>{
        val stringDate = "${date.year}-${date.monthValue+1}-${date.dayOfMonth}"
        return serviceForInfo.getInfoByGameDay(stringDate).dates.flatMap { it.games }
    }
    suspend fun getAllLogos(): List<LogoFromApi> {
        return serviceForLogos.getLogos().data.flatMap { it.teams.flatMap { it.logos } }
    }
    suspend fun getAllTeamsInfo(): List<TeamsInfoFromSecondAPI>{
        return serviceForTeamsAPI.getTeamsInfoBySecondAPI()
    }
    suspend fun getStadiumInfo(): List<StadiumInfo>{
        return serviceForTeamsAPI.getStadiumInfoBySecondAPI()
    }
    }



//    suspend fun getTeamLogo(teamId: Int): String?{
//        val listLogos = serviceForLogos.getLogos().data.flatMap { it.teams.flatMap { it.logos } }
//        val teamLogos = listLogos.filter { it.teamId == teamId }
//        val logo = teamLogos.maxByOrNull { it.endSeason }
//        return logo?.url
//    }
