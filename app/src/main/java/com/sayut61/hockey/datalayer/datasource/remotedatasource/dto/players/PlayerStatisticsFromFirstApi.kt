package com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.players

import com.sayut61.hockey.datalayer.datasource.remotedatasource.dto.teams.Splits

data class PlayerStatisticsFromFirstApi(
    val stats: List<Stat>
)
data class Stat(
    val splits: List<Statistics>
)
data class Statistics(
    val timeOnIce: String,
    val assists: Int,
    val goals: Int,
    val shots: Int,
    val games: Int,
    val hits: Int,
    val powerPlayGoals: Int,
    val powerPlayPoints: Int,
    val powerPlayTimeOnIce: String,
    val blocked: Int,
    val plusMinus: Int,
    val points: Int,
    val timeOnIcePerGame: String,

)