package com.sayut61.hockey.domain.entities

data class PlayerStatisticsInfo(
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
    val timeOnIcePerGame: String
)