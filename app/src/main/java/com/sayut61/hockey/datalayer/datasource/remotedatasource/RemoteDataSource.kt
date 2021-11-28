package com.sayut61.hockey.datalayer.datasource.remotedatasource

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.games.*
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.players.*
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.stadium.StadiumGeneralInfo
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

    private interface RestNHLInfoFirstAPI {
        @GET(value = "/api/v1/teams")
        suspend fun getAllTeams(): TeamsInfoFromFirstApiResponse

        @GET(value = "/api/v1/schedule")
        suspend fun getInfoByGameDay(@Query("date") date: String): GamesResponse

        @GET(value = "{link}")
        suspend fun getDetailInfoByGame(@Path("link", encoded = true) link: String): GameDetailResponse

        @GET(value = "/api/v1/teams?expand=team.roster")
        suspend fun getListAllPlayers(): PlayersGeneralInfo

        @GET(value = "/api/v1/teams/{id}?expand=team.stats")
        suspend fun getDetailInfoByTeam(@Path("id", encoded = true)id: Int): TeamFullInfoFromFirstApiResponse

        @GET(value = "/api/v1/teams/{id}/roster")
        suspend fun getPlayersInfoByTeam(@Path("id", encoded = true)id: Int): TeamPlayersInfoFromApi

        @GET(value = "/api/v1/people/{playerId}")
        suspend fun getPlayerFullInfo(@Path("playerId", encoded = true)playerId: Int): PlayerInfo

        @GET(value = "/api/v1/people/{id}/stats?stats=statsSingleSeason&season=20212022")
        suspend fun getPlayerStatistic(@Path("id", encoded = true)playerId: Int): PlayerStatisticsFromFirstApi
    }

    private interface RestNHLInfoSecondAPI {
        @GET(value = "teams?key=1dd2b753fe264d2b9d7d08d0988b34e2")
        suspend fun getTeamsInfoBySecondApi(): List<TeamGeneralInfoFromSecondApi>

        @GET(value = "Stadiums?key=1dd2b753fe264d2b9d7d08d0988b34e2")
        suspend fun getStadiumInfoBySecondApi(): List<StadiumGeneralInfo>

        @GET(value = "Players?key=1dd2b753fe264d2b9d7d08d0988b34e2")
        suspend fun getPlayersPhoto(): List<PlayerInfoFromSecondApi>
    }

    //Retrofit

    val client = OkHttpClient()
        .newBuilder()
        .addInterceptor(ErrorInterceptor())
        .build()
    private var retrofitFirstApiInfo = Retrofit.Builder()
        .baseUrl("https://statsapi.web.nhl.com")
        .client(client)
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
    suspend fun getPlayerStatistics(playerId: Int): PlayerStatisticsFromFirstApi{
        return serviceForFirstApi.getPlayerStatistic(playerId)
    }

    suspend fun getPlayersPhotos(): List<PlayerInfoFromSecondApi>{
        return serviceForSecondApi.getPlayersPhoto()
    }

    suspend fun getPlayerFullInfo(playerId: Int): PlayerFullInfoFromApi{
        val player = serviceForFirstApi.getPlayerFullInfo(playerId)
        return playerInfoToPlayerFullInfo(player)
    }

    suspend fun getPlayersInfoByTeam(teamId: Int): List<TeamPlayers>{
        val teamPlayers = serviceForFirstApi.getPlayersInfoByTeam(teamId)
        return teamPlayersInfoFromApiToTeamPlayers(teamPlayers)
    }

    suspend fun getTeamFullInfo(teamId: Int): FullInfoByTeam{
        val teamInfoResponse = serviceForFirstApi.getDetailInfoByTeam(teamId)
        val result =  teamFullInfoFromFirstApiResponseToFullInfoByTeams(teamInfoResponse)
        return result
    }

    suspend fun getListPlayers(): List<PlayerGeneralInfoFromApi>{
        val playersResponse = serviceForFirstApi.getListAllPlayers()
        return playersGenInfoToAllPlayersGeneralInfo(playersResponse)
    }

    suspend fun getTeamsFirstApi(): List<TeamInfoFromFirstApi> {
        return serviceForFirstApi.getAllTeams().teams
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getGamesByDate(date: LocalDate): List<GameFromFirstApi> {
        val stringDate = "${date.year}-${date.monthValue}-${date.dayOfMonth}"
        val gamesResponse = serviceForFirstApi.getInfoByGameDay(stringDate)
        return gamesResponseToGamesFromFirstApi(gamesResponse)
    }
    suspend fun getGameDetails(link: String): FullInfoByGame {
        val gameResponse = serviceForFirstApi.getDetailInfoByGame(link)
        Log.d("myLog", gameResponse.toString())
        return gameDetailResponseToFullInfoByGame(gameResponse)
    }
    suspend fun getTeamsSecondApi(): List<TeamGeneralInfoFromSecondApi> {
        return serviceForSecondApi.getTeamsInfoBySecondApi()
    }
    suspend fun getStadiumInfo(): List<StadiumGeneralInfo> {
        return serviceForSecondApi.getStadiumInfoBySecondApi()
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
