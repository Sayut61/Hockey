package com.sayut61.hockey.datalayer.datasource.remotedatasource

import android.os.Build
import androidx.annotation.RequiresApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.TeamsInfo.TeamInfoFromSecondApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.calendar.GamesDateResponse
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.calendar.Games
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.stadium.StadiumInfo
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams.TeamInfoFromFirstApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams.TeamsInfoFromFirstApiResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate
import javax.inject.Inject
class RemoteDataSource @Inject constructor() {

    //Retrofit

    private interface RestNHLInfoFirstAPI {
        @GET(value = "v1/teams")
        suspend fun getAllTeams(): TeamsInfoFromFirstApiResponse

        @GET(value = "v1/schedule")
        suspend fun getInfoByGameDay(@Query("date") date: String): GamesDateResponse
    }

    private interface RestNHLInfoSecondAPI{
        @GET(value = "teams?key=1dd2b753fe264d2b9d7d08d0988b34e2")
        suspend fun getTeamsInfoBySecondApi(): List<TeamInfoFromSecondApi>

        @GET(value = "Stadiums?key=1dd2b753fe264d2b9d7d08d0988b34e2")
        suspend fun getStadiumInfoBySecondApi(): List<StadiumInfo>
    }

    //Retrofit

    private var retrofitFirstApiInfo = Retrofit.Builder()
        .baseUrl("https://statsapi.web.nhl.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private var retrofitSecondApiInfo = Retrofit.Builder()
        .baseUrl("https://api.sportsdata.io/v3/nhl/scores/json/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //Retrofit

    private var serviceForFirstApi = retrofitFirstApiInfo.create(RestNHLInfoFirstAPI::class.java)
    private var serviceForSecondApi = retrofitSecondApiInfo.create(RestNHLInfoSecondAPI::class.java)

    //Retrofit

    suspend fun getTeamsFirstApi(): List<TeamInfoFromFirstApi> {
        return serviceForFirstApi.getAllTeams().teams
    }
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getGamesByDate(date: LocalDate): List<Games>{
        val stringDate = "${date.year}-${date.monthValue+1}-${date.dayOfMonth}"
        return serviceForFirstApi.getInfoByGameDay(stringDate).dates.flatMap { it.games }
    }
    suspend fun getTeamsSecondApi(): List<TeamInfoFromSecondApi>{
        return serviceForSecondApi.getTeamsInfoBySecondApi()
    }
    suspend fun getStadiumInfo(): List<StadiumInfo>{
        return serviceForSecondApi.getStadiumInfoBySecondApi()
    }
}

//https://statsapi.web.nhl.com/api/v1/game/2021020001/feed/live