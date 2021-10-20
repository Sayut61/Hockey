package com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.calendar

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class FullInfoByGame(
//    val players: List<String>,
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
    val codedGameState: Int
)

fun gameDetailResponseToFullInfoByGame(gameDetailResponse: GameDetailResponse): FullInfoByGame {
    return FullInfoByGame(
//        players = gameDetailResponse.gameData.players,
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
        codedGameState = gameDetailResponse.gameData.status.codedGameState
    )
}

data class GameDetailResponse(
    val gameData: GameData,
    val liveData: GameLiveData
)
data class GameData(
    val status: StatusGame
)
data class StatusGame(
    val codedGameState: Int
)
// Список игроков, НУЖНО ПРОВЕРИТЬ!!!
//data class GameData(
//    val players: List<Player>,
//)
//data class Player(
//    @SerializedName("id")
//    val playerId: Int,
//    val fullName
//)
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
    val teamStats: TeamAwaySkaterStats
)
data class HomeTeamStats(
    val teamStats: TeamHomeSkaterStats
)
data class TeamAwaySkaterStats(
    val teamSkaterStats: TeamAwaySkaterStatss
)
data class TeamHomeSkaterStats(
    val teamSkaterStats: TeamHomeSkaterStatss
    )
data class TeamAwaySkaterStatss(
    val goals: Int,
    val shots: Int,
    val blocked: Int,
    val hits: Int
)
data class TeamHomeSkaterStatss(
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
    val awayTeam: AwayTeamScore,
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

