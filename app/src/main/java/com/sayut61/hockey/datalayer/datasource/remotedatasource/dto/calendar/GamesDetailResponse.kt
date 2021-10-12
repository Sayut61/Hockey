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
    val totalGoalsAwayTeam: Int,
    val totalGoalsHomeTeam: Int
)

fun gameDetailResponseToFullInfoByGame(gameDetailResponse: GameDetailResponse): FullInfoByGame{
    val periodsNumber = gameDetailResponse.liveData.lineScore.periods.map{ it.periodNumber }
    val fullGameInfo = FullInfoByGame(
        players = gameDetailResponse.gameData.players,
        currentPeriod = gameDetailResponse.liveData.lineScore.currentPeriod,
        currentPeriodOrdinal = gameDetailResponse.liveData.lineScore.currentPeriodOrdinal,
        currentPeriodTimeRemaining = gameDetailResponse.liveData.lineScore.currentPeriodTimeRemaining,
        periodNumber = periodsNumber.map {  },
        homeTeamGoalsByCurrentPeriod =
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
    val lineScore: CurrentInfo
)
// Корректная информация по игре в реальном времени (период, время периода)
data class CurrentInfo(
    val currentPeriod: Int,
    val currentPeriodOrdinal: String,
    val currentPeriodTimeRemaining: String,
    val periods: List<PeriodsInfo>,
    @SerializedName("shootoutInfo")
    val fullGameScore: FullGameScore
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
// Счет по всему матчу
data class FullGameScore(
    @SerializedName("away")
    val awayTeam: ScoreAway,
    @SerializedName("home")
    val homeTeam: ScoreHome
)
data class ScoreAway(
    @SerializedName("scores")
    val goalsAwayTeam: Int
)
data class ScoreHome(
    @SerializedName("scores")
    val goalsHomeTeam: Int
)

