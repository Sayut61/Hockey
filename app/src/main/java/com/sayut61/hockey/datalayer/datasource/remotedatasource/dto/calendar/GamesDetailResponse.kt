package com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.calendar

import com.google.gson.annotations.SerializedName

data class FullInfoByGame(
    val players: List<String>,
    val currentPeriod: Int,
    val currentPeriodOrdinal: String,
    val currentPeriodTimeRemaining: String,
    val periodNumber: Int,
    val homeTeamGoalsByCurrentPeriod: Int,
    val homeTeamShotsOnGoalByCurrentPeriod: Int,
    val awayTeamGoalsByCurrentPeriod: Int,
    val awayTeamShotsOnGoalByCurrentPeriod: Int,
    val goalsAwayTeam: Int,
    val shotsAwayTeam: Int,
    val blockedAwayTeam: Int,
    val hitsAwayTeam: Int,
    val goalsHomeTeam: Int,
    val shotsHomeTeam: Int,
    val blockedHomeTeam: Int,
    val hitsHomeTeam: Int
)

fun gameDetailResponseToFullInfoByGame(gameDetailResponse: GameDetailResponse): FullInfoByGame{
    val periodsNumber = gameDetailResponse.liveData.lineScore.periods.map{ it.periodNumber }
    val fullGameInfo = FullInfoByGame(
        players = gameDetailResponse.gameData.players,
        currentPeriod = gameDetailResponse.liveData.lineScore.currentPeriod,
        currentPeriodOrdinal = gameDetailResponse.liveData.lineScore.currentPeriodOrdinal,
        currentPeriodTimeRemaining = gameDetailResponse.liveData.lineScore.currentPeriodTimeRemaining,
        periodNumber = periodsNumber.
    )
}

data class GameDetailResponse(
    val gameData: GameData,
    val liveData: GameLiveData
)
// Список игроков, НУЖНО ПРОВЕРИТЬ!!!
data class GameData(
    val players: List<String>,
)
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
    val goals: Int,
    val shots: Int,
    val blocked: Int,
    val hits: Int
)
data class TeamHomeSkaterStats(
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
data class PeriodsInfo(
    @SerializedName("num")
    val periodNumber: Int,
    @SerializedName("home")
    val homeTeam: HomeTeamScore,
    @SerializedName("away")
    val awayTeam: AwayTeamScore,
)
data class HomeTeamScore(
    val goals: Int,
    val shotsOnGoal: Int
)
data class AwayTeamScore(
    val goals: Int,
    val shotsOnGoal: Int
)

