package com.sayut61.hockey.datalayer.datasource.loacaldatasource.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteGame(
    val gameDate: String,
    val linkOnDetailInfoByGame: String,
    val awayTeamNameFull: String,
    val awayTeamId: Int,
    val homeTeamNameFull: String,
    val homeTeamId: Int,
    @PrimaryKey
    val gameId: Int,
    val homeTeamLogo: String?,
    val awayTeamLogo: String?
)