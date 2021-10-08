package com.sayut61.hockey.datalayer.datasource.loacaldatasource.dto

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteGame(
    val gameDate: String,
    val linkOnDetailInfoByGame: String,
    val awayTeamName: String,
    val awayTeamId: Int,
    val homeTeamName: String,
    val homeTeamId: Int,
    @PrimaryKey
    val gameId: Int,
    val homeTeamLogo: String?,
    val awayTeamLogo: String?
)