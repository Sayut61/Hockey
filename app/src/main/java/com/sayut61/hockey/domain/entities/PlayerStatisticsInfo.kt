package com.sayut61.hockey.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlayerStatisticsInfo(
    val id: Int,
    val name: String,
    val photo: String? = null,
    val timeOnIce: String?= "0",
    val assists: Int?= 0,
    val goals: Int?= 0,
    val shots: Int?= 0,
    val games: Int?= 0,
    val hits: Int?= 0,
    val powerPlayGoals: Int?= 0,
    val powerPlayPoints: Int?= 0,
    val powerPlayTimeOnIce: String?= "0",
    val blocked: Int?= 0,
    val plusMinus: Int?= 0,
    val points: Int?= 0,
    val timeOnIcePerGame: String?= "0"
): Parcelable