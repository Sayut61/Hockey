package com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.calendar

import com.google.gson.annotations.SerializedName
data class FullInfoByGame(
    val players: List<String>,
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
    val hitsHomeTeam: Int
)

fun gameDetailResponseToFullInfoByGame(gameDetailResponse: GameDetailResponse): FullInfoByGame {
    return FullInfoByGame(
        players = gameDetailResponse.gameData.players,
        currentPeriod = gameDetailResponse.liveData.lineScore.currentPeriod,
        currentPeriodOrdinal = gameDetailResponse.liveData.lineScore.currentPeriodOrdinal,
        currentPeriodTimeRemaining = gameDetailResponse.liveData.lineScore.currentPeriodTimeRemaining,
        periods = gameDetailResponse.liveData.lineScore.periods,
        homeTeamGoalsByPeriods = gameDetailResponse.liveData.lineScore.periods.map { it.homeTeam.goals },
        homeTeamShotsOnGoalByPeriods = gameDetailResponse.liveData.lineScore.periods.map { it.homeTeam.shotsOnGoal },
        awayTeamGoalsByPeriods = gameDetailResponse.liveData.lineScore.periods.map { it.awayTeam.goals },
        awayTeamShotsOnGoalByPeriods = gameDetailResponse.liveData.lineScore.periods.map { it.awayTeam.shotsOnGoal },
        goalsAwayTeam = gameDetailResponse.liveData.boxScore.teams.away.teamStats.goals,
        goalsHomeTeam = gameDetailResponse.liveData.boxScore.teams.home.teamStats.goals,
        shotsAwayTeam = gameDetailResponse.liveData.boxScore.teams.away.teamStats.shots,
        shotsHomeTeam = gameDetailResponse.liveData.boxScore.teams.home.teamStats.shots,
        blockedAwayTeam = gameDetailResponse.liveData.boxScore.teams.away.teamStats.blocked,
        blockedHomeTeam = gameDetailResponse.liveData.boxScore.teams.home.teamStats.blocked,
        hitsAwayTeam = gameDetailResponse.liveData.boxScore.teams.away.teamStats.hits,
        hitsHomeTeam = gameDetailResponse.liveData.boxScore.teams.home.teamStats.hits
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

