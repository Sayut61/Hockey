package com.sayut61.hockey.datalayer.datasource.remotedatasource

import android.os.Build
import android.util.Log
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import com.google.gson.*
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.games.*
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.players.PlayerGeneralInfoFromApi
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.players.PlayersGeneralInfo
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.players.playersGenInfoToAllPlayersGeneralInfo
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.stadium.StadiumGeneralInfo
import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams.*
import com.sayut61.hockey.domain.entities.TeamPlayersInfo
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.lang.reflect.Type
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


    }

    private interface RestNHLInfoSecondAPI {
        @GET(value = "teams?key=1dd2b753fe264d2b9d7d08d0988b34e2")
        suspend fun getTeamsInfoBySecondApi(): List<TeamGeneralInfoFromSecondApi>

        @GET(value = "Stadiums?key=1dd2b753fe264d2b9d7d08d0988b34e2")
        suspend fun getStadiumInfoBySecondApi(): List<StadiumGeneralInfo>
    }

    //Retrofit

    val client = OkHttpClient()
        .newBuilder()
        .addInterceptor(ErrorInterceptor())
        .build()
    fun createGsonConverter(): Converter.Factory? {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(Player::class.java, RedirectionInfoDeserializer())
        val gson = gsonBuilder.create()
        return GsonConverterFactory.create(gson)
    }
    private var retrofitFirstApiInfo = Retrofit.Builder()
        .baseUrl("https://statsapi.web.nhl.com")
        .client(client)
        .addConverterFactory(createGsonConverter())
        .build()
    private var retrofitSecondApiInfo = Retrofit.Builder()
        .baseUrl("https://api.sportsdata.io/v3/nhl/scores/json/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //Retrofit

    private var serviceForFirstApi = retrofitFirstApiInfo.create(RestNHLInfoFirstAPI::class.java)
    private var serviceForSecondApi = retrofitSecondApiInfo.create(RestNHLInfoSecondAPI::class.java)

    //Retrofit
    suspend fun getPlayersInfoByTeam(teamId: Int): List<TeamPlayers>{
        val teamPlayers = serviceForFirstApi.getPlayersInfoByTeam(teamId)
        return teamPlayersInfoFromApiToTeamPlayers(teamPlayers)
    }

    suspend fun getTeamFullInfo(teamId: Int): FullInfoByTeam{
        val teamInfoResponse = serviceForFirstApi.getDetailInfoByTeam(teamId)
        return teamFullInfoFromFirstApiResponseToFullInfoByTeams(teamInfoResponse)
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
        if (response.code() >= 400)
            Log.d("requestError", "Request: $request \n Response: $response")
        return response
    }
}

//https://statsapi.web.nhl.com/api/v1/game/2021020001/feed/live


internal class RedirectionInfoDeserializer : JsonDeserializer<GameData> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): GameData {
        val jsonObject = json.asJsonObject

        // Read simple String values.
        //val uri = jsonObject[KEY_URI].asString

        // Read the dynamic parameters object.
        val parameters = readParametersMap(jsonObject)
        val result = GameData(StatusGame(hashCode()))
        return result
    }

    @Nullable
    private fun readParametersMap(jsonObject: JsonObject): Map<String, Player>? {
        val paramsElement = jsonObject[KEY_PARAMETERS]
            ?: // value not present at all, just return null
            return null
        val parametersObject = paramsElement.asJsonObject
        val parameters: MutableMap<String, Player> = HashMap()
        for ((key, value1) in parametersObject.entrySet()) {
            val value = Json.decodeFromString<Player>(Player.serializer(), value1.asString)
            parameters[key] = value
        }
        return parameters
    }

    companion object {
        private const val KEY_PARAMETERS = "players"
    }
}