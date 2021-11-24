package com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.games

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sayut61.hockey.domain.entities.PlayerNameAndNumber
import kotlinx.parcelize.Parcelize

data class FullInfoByGame(
    val playersAwayTeam: List<PlayerNameAndNumber>?,
    val playersHomeTeam: List<PlayerNameAndNumber>?,
    val currentPeriod: Int,
    val currentPeriodOrdinal: String,
    val currentPeriodTimeRemaining: String,
    val periods: List<PeriodsInfo>,
    val homeTeamGoalsByPeriods: List<Int>,
    val homeTeamShotsOnGoalByPeriods: List<Int>,
    val awayTeamGoalsByPeriods: List<Int>,
    val awayTeamShotsOnGoalByPeriods: List<Int>,
    val goalsAwayTeam: Int,
    val shotsAwayTeam: Int,
    val blockedAwayTeam: Int,
    val hitsAwayTeam: Int,
    val goalsHomeTeam: Int,
    val shotsHomeTeam: Int,
    val blockedHomeTeam: Int,
    val hitsHomeTeam: Int,
    val codedGameState: Int,
)
fun gameDetailResponseToFullInfoByGame(gameDetailResponse: GameDetailResponse): FullInfoByGame {
    val playersMap: Map<String, Player>? = gameDetailResponse.gameData.players
    val listPlayers: List<Player>? = playersMap?.values?.toList()
    val awayTeamPlayers = listPlayers?.filter { it.currentTeam.id == gameDetailResponse.liveData.boxScore.teams.away.team.id }
    val homeTeamPlayers = listPlayers?.filter { it.currentTeam.id == gameDetailResponse.liveData.boxScore.teams.home.team.id }
    return FullInfoByGame(
        playersAwayTeam = awayTeamPlayers?.map{PlayerNameAndNumber(it.fullName, it.primaryNumber, it.playerId)},
        playersHomeTeam = homeTeamPlayers?.map{PlayerNameAndNumber(it.fullName, it.primaryNumber, it.playerId)},
        currentPeriod = gameDetailResponse.liveData.lineScore.currentPeriod,
        currentPeriodOrdinal = gameDetailResponse.liveData.lineScore.currentPeriodOrdinal,
        currentPeriodTimeRemaining = gameDetailResponse.liveData.lineScore.currentPeriodTimeRemaining,
        periods = gameDetailResponse.liveData.lineScore.periods,
        homeTeamGoalsByPeriods = gameDetailResponse.liveData.lineScore.periods.map { it.homeTeam.goals },
        homeTeamShotsOnGoalByPeriods = gameDetailResponse.liveData.lineScore.periods.map { it.homeTeam.shotsOnGoal },
        awayTeamGoalsByPeriods = gameDetailResponse.liveData.lineScore.periods.map { it.awayTeam.goals },
        awayTeamShotsOnGoalByPeriods = gameDetailResponse.liveData.lineScore.periods.map { it.awayTeam.shotsOnGoal },
        goalsAwayTeam = gameDetailResponse.liveData.boxScore.teams.away.teamStats.teamSkaterStats.goals,
        goalsHomeTeam = gameDetailResponse.liveData.boxScore.teams.home.teamStats.teamSkaterStats.goals,
        shotsAwayTeam = gameDetailResponse.liveData.boxScore.teams.away.teamStats.teamSkaterStats.shots,
        shotsHomeTeam = gameDetailResponse.liveData.boxScore.teams.home.teamStats.teamSkaterStats.shots,
        blockedAwayTeam = gameDetailResponse.liveData.boxScore.teams.away.teamStats.teamSkaterStats.blocked,
        blockedHomeTeam = gameDetailResponse.liveData.boxScore.teams.home.teamStats.teamSkaterStats.blocked,
        hitsAwayTeam = gameDetailResponse.liveData.boxScore.teams.away.teamStats.teamSkaterStats.hits,
        hitsHomeTeam = gameDetailResponse.liveData.boxScore.teams.home.teamStats.teamSkaterStats.hits,
        codedGameState = gameDetailResponse.gameData.status.codedGameState,
    )
}

data class GameDetailResponse(
    val gameData: GameData,
    val liveData: GameLiveData
    )
data class GameData(
    val status: StatusGame,
    val players: Map<String, Player>?
    )
data class StatusGame(
    val codedGameState: Int
    )
data class Player(
    @SerializedName("id")
    val playerId: Int,
    val fullName: String,
    val primaryNumber: String,
    val currentTeam: CurrentTeam
    )
data class CurrentTeam(
    val id: Int
    )
//____________________________________
data class GameLiveData(
    @SerializedName("linescore")
    val lineScore: CurrentInfo,
    @SerializedName("boxscore")
    val boxScore: TeamsScore
    )
// Информация по общим игровым моментам за всю игру(голы, броски, блокированные броски, силовые приемы)
data class TeamsScore(
    val teams: HomeOrAwayTeamScore
    )
data class HomeOrAwayTeamScore(
    val away: TeamAwayStats,
    val home: HomeTeamStats
    )
data class TeamAwayStats(
    val team: Team,
    val teamStats: TeamAwaySkaterStats
    )
data class Team(
    val id: Int
    )
data class HomeTeamStats(
    val team: Team,
    val teamStats: TeamHomeSkaterStats
    )
data class TeamAwaySkaterStats(
    val teamSkaterStats: TeamAwaySkaterState
    )
data class TeamHomeSkaterStats(
    val teamSkaterStats: TeamHomeSkaterState
    )
data class TeamAwaySkaterState(
    val goals: Int,
    val shots: Int,
    val blocked: Int,
    val hits: Int
)
data class TeamHomeSkaterState(
    val goals: Int,
    val shots: Int,
    val blocked: Int,
    val hits: Int
    )
// Корректная информация по игре в реальном времени (период, время периода)
data class CurrentInfo(
    val currentPeriod: Int,
    val currentPeriodOrdinal: String,
    val currentPeriodTimeRemaining: String,
    val periods: List<PeriodsInfo>,
)
// Информация по периоду. Кто сколько забил в каждом периоде
@Parcelize
data class PeriodsInfo(
    @SerializedName("num")
    val periodNumber: Int,
    @SerializedName("home")
    val homeTeam: HomeTeamScore,
    @SerializedName("away")
    val awayTeam: AwayTeamScore
    ): Parcelable
@Parcelize
data class HomeTeamScore(
    val goals: Int,
    val shotsOnGoal: Int
    ): Parcelable
@Parcelize
data class AwayTeamScore(
    val goals: Int,
    val shotsOnGoal: Int
    ): Parcelable

