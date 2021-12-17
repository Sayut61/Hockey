package com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.players

data class PlayerStatisticsFromFirstApiResponse(
    val stats: List<Stats>
)
data class Stats(
    val splits: List<Splits>
)
data class Splits(
    val stat: Stat
)

data class Stat(
    val timeOnIce: String?,
    val assists: Int?,
    val goals: Int?,
    val shots: Int?,
    val games: Int?,
    val hits: Int?,
    val powerPlayGoals: Int?,
    val powerPlayPoints: Int?,
    val powerPlayTimeOnIce: String?,
    val blocked: Int?,
    val plusMinus: Int?,
    val points: Int?,
    val timeOnIcePerGame: String?
)