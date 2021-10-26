package com.sayut61.hockey.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Player(
    val teamId: Int,
    val teamFullName: String,
    val teamShortName: String,
    val jerseyNumber: Int,
    val playerId: Int,
    val fullName: String,
    val linkOnPlayerDetailInfo: String,
    val logo: String?
) : Parcelable