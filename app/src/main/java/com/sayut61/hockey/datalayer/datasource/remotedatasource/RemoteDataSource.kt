package com.sayut61.hockey.datalayer.datasource.remotedatasource

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.games.*
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.players.*
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.stadium.Stadium
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate
import javax.inject.Inject


class RemoteDataSource @Inject constructor() {

    //Retrofit

    private interface RestNHLFirstAPI {
        @GET(value = "/api/v1/teams")
        suspend fun getAllTeams(): TeamsFromFirstApi

        @GET(value = "/api/v1/schedule")
        suspend fun getGamesByDate(@Query("date") date: String): GamesResponse

        @GET(value = "{link}")
        suspend fun getGameDetails(@Path("link", encoded = true) link: String): GameFullInfoResponse

        @GET(value = "/api/v1/teams?expand=team.roster")
        suspend fun getAllPlayers(): PlayersGeneralInfoResponse

        @GET(value = "/api/v1/teams/{id}?expand=team.stats")
        suspend fun getTeamFullInfo(@Path("id", encoded = true)teamId: Int): TeamFullInfoFromFirstApiResponse

        @GET(value = "/api/v1/teams/{id}/roster")
        suspend fun getPlayersByTeam(@Path("id", encoded = true)teamId: Int): TeamPlayersFromFirstApiResponse

        @GET(value = "/api/v1/people/{playerId}")
        suspend fun getPlayerFullInfo(@Path("playerId", encoded = true)playerId: Int): PlayerFullInfoResponse

        @GET(value = "/api/v1/people/{id}/stats?stats=statsSingleSeason&season=20212022")
        suspend fun getPlayerStatistic(@Path("id", encoded = true)playerId: Int): PlayerStatisticsFromFirstApiResponse
    }

    private interface RestNHLSecondAPI {
        @GET(value = "teams?key=1dd2b753fe264d2b9d7d08d0988b34e2")
        suspend fun getTeamsGeneralInfo(): List<TeamGeneralInfoFromSecondApi>

        @GET(value = "Stadiums?key=1dd2b753fe264d2b9d7d08d0988b34e2")
        suspend fun getStadiums(): List<Stadium>

        @GET(value = "Players?key=1dd2b753fe264d2b9d7d08d0988b34e2")
        suspend fun getPlayersPhoto(): List<PlayerPhoto>
    }

    //Retrofit

    val client = OkHttpClient()
        .newBuilder()
        .addInterceptor(ErrorInterceptor())
        .build()
    private var retrofitFirstApi = Retrofit.Builder()
        .baseUrl("https://statsapi.web.nhl.com")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private var retrofitSecondApi = Retrofit.Builder()
        .baseUrl("https://api.sportsdata.io/v3/nhl/scores/json/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //Retrofit

    private var serviceForFirstApi = retrofitFirstApi.create(RestNHLFirstAPI::class.java)
    private var serviceForSecondApi = retrofitSecondApi.create(RestNHLSecondAPI::class.java)

    //Retrofit
    suspend fun getPlayerStatistic(playerId: Int): PlayerStatisticsFromFirstApiResponse{
        return serviceForFirstApi.getPlayerStatistic(playerId)
    }

    suspend fun getPlayersPhoto(): List<PlayerPhoto>{
        return serviceForSecondApi.getPlayersPhoto()
    }

    suspend fun getPlayerFullInfo(playerId: Int): PlayerFullInfoFromApi{
        val player = serviceForFirstApi.getPlayerFullInfo(playerId)
        return playerInfoToPlayerFullInfo(player)
    }

    suspend fun getPlayersByTeam(teamId: Int): List<TeamPlayersFromApi>{
        val teamPlayers = serviceForFirstApi.getPlayersByTeam(teamId)
        return teamPlayersInfoFromApiToTeamPlayers(teamPlayers)
    }

    suspend fun getTeamFullInfo(teamId: Int): TeamFullInfoFromApi{
        val teamInfoResponse = serviceForFirstApi.getTeamFullInfo(teamId)
        return teamFullInfoFromFirstApiResponseToFullInfoByTeams(teamInfoResponse)
    }

    suspend fun getAllPlayers(): List<PlayerGeneralInfoFromApi>{
        val playersResponse = serviceForFirstApi.getAllPlayers()
        return playersGenInfoToAllPlayersGeneralInfo(playersResponse)
    }

    suspend fun getAllTeamsFromFirstApi(): List<TeamInfoFromFirstApi> {
        return serviceForFirstApi.getAllTeams().teams
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getGamesByDate(date: LocalDate): List<GameFromFirstApi> {
        val stringDate = "${date.year}-${date.monthValue}-${date.dayOfMonth}"
        val gamesResponse = serviceForFirstApi.getGamesByDate(stringDate)
        return gamesResponseToGamesFromFirstApi(gamesResponse)
    }
    suspend fun getGameDetails(link: String): GameFullInfoFromApi {
        val gameResponse = serviceForFirstApi.getGameDetails(link)
        Log.d("myLog", gameResponse.toString())
        return gameFullInfoResponseToGameFullInfo(gameResponse)
    }
    suspend fun getAllTeamsFromSecondApi(): List<TeamGeneralInfoFromSecondApi> {
        return serviceForSecondApi.getTeamsGeneralInfo()
    }
    suspend fun getStadiums(): List<Stadium> {
        return serviceForSecondApi.getStadiums()
    }
}

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request: Request = chain.request()
        val response = chain.proceed(request)
        Log.d("myLog", "Request: $request \n Response: $response")
        if (response.code() >= 400)
            Log.d("requestError", "Request: $request \n Response: $response")
        return response
    }
}
