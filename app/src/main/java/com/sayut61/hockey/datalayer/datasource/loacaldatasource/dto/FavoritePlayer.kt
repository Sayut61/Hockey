package com.sayut61.hockey.datalayer.datasource.loacaldatasource.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class FavoritePlayer (
    @PrimaryKey
    val teamId: Int,
    val teamFullName: String,
    val teamShortName: String,
    val jerseyNumber: Int,
    val playerId: Int,
    val fullName: String,
    val linkOnPlayerDetailInfo: String,
    val logo: String?
    )