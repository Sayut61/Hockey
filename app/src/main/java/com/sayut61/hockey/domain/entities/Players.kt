package com.sayut61.hockey.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Players(
    val teamId: Int,
    val teamFullName: String,
    val teamShortName: String,
    val jerseyNumber: List<Int>,
    val playerId: List<Int>,
    val fullName: List<String>,
    val linkOnPlayerDetailInfo: List<String>
): Parcelable